package main

import java.util.regex.{ Pattern, Matcher }


class LexerOptions(val stripnl: Boolean = true,
					val stripall: Boolean = false,
					val ensurenl: Boolean = true,
					val tabsize: Int = 0,
					val encoding: String = "UTF-8",
					val filters: List[Filter] = Nil)

trait Lexer {
    val options: LexerOptions

	var filters = options.filters

	val name: String

	val aliases: List[String] = Nil

	val filenames: List[String] = Nil

	val aliasFilenames: List[String] = Nil

	val mimetypes: List[String] = Nil

	def addFilter(filter: Filter) = {
		filters = filter :: filters
	}

	/**
		Return an iterable of (tokentype, value) pairs generated from
        'text'. If 'unfiltered' is set to 'True', the filtering mechanism
        is bypassed even if filters are defined.

        Also preprocess the text, i.e. expand tabs and strip it if
        wanted and applies registered filters.
	*/
	def getTokens(t: String, unfiltered: Boolean = false) = {
        import Filter._

        var text = t.replaceAll("\r\n", "\n").replaceAll("\r", "\n")
        
        if (options.stripall) text = text.trim
        else if(options.stripnl) text = text.stripLineEnd.reverse.stripLineEnd.reverse // =^_^=
        if(options.tabsize > 0) text = text.replaceAll("\t", " " * options.tabsize)
        if(options.ensurenl && !text.endsWith("\n")) text = text + "\n"

        var stream = getTokensUnprocessed(text).map(t => (t._2, t._3))
        if (!unfiltered) stream = applyFilters(stream, filters, Some(this))
        
        stream
    }

    /**
    	Return an iterable of (tokentype, value) pairs.
        In subclasses, implement this method as a generator to
        maximize effectiveness.
    */
    def getTokensUnprocessed(text: String): List[(Int, Token, String)]
	
}

trait TextAnalyser {

	def analyse_text(text: String): Float
}

abstract sealed class StateDef 

case class Include(state: String) extends StateDef

case class TokenDefs(list: List[Definition]) extends StateDef 

trait StateDefHelpers {
    implicit def ListToTokenDefs(list: List[Definition]): StateDef = TokenDefs(list)
}

abstract sealed class Desc

case class IsToken(t: Token) extends Desc
case class ByGroups(tt: Token*) extends Desc 

trait DescHelpers {
    implicit def TokenToIsToken(t: Token):Desc = IsToken(t)
}

abstract sealed class Definition

case class WithAction(state: (String, Desc, Action)) extends Definition
case class WithoutAction(state: (String, Desc)) extends Definition

trait DefinitionHelpers {
    implicit def TupleToWithAction(state: (String, Desc, Action)):Definition = WithAction(state)
    implicit def TupleToWithoutAction(state: (String, Desc)):Definition = WithoutAction(state)
    implicit def TupleToWithoutAction2(state: (String, Token)):Definition = WithoutAction((state._1, IsToken(state._2)))
}

abstract sealed class Action

case class GoTo(str: String) extends Action

case class Pop(steps: Int) extends Action with Popper

trait ActionHelpers {
    implicit def StringToAction(state: String):Action = GoTo(state)
}

trait Popper {
    def steps: Int
}

object Pop extends Action with Popper {
    val steps = 1
}

object Push extends Action {

}

object DoNothing extends Action


/*
    Base for simple stateful regular expression-based lexers.
    Simplifies the lexing process so that you need only
    provide a list of states and regular expressions.
*/
trait RegexLexer extends Lexer {
    val tokens: Map[String, StateDef]

    val flags = Pattern.MULTILINE
    
    private var _tmpname = 0

    lazy val _tokens = processTokendef(tokens)

    private def processTokendef(tokendefs: Map[String, StateDef]) = {
        
        val processed = scala.collection.mutable.Map[String, List[((String) => Matcher, Desc, Action)]]()
        for(state <- tokendefs.keys) {
            //println("begin process %s" format state)
            processed(state) = processState(tokendefs, processed, state)
        }
        
        processed
    }

    private def processState(unprocessed: Map[String, StateDef], 
                            processed: scala.collection.mutable.Map[String, List[((String) => Matcher, Desc, Action)]], 
                            state: String): List[((String) => Matcher, Desc, Action)] = {

        if (processed.contains(state)) processed(state)
        else {

         
            unprocessed(state) match {
                case Include(otherState) => {
                    //println("Include " + otherState)
                    processState(unprocessed, processed, otherState)
                }
                
                case TokenDefs(list) => {
                    //println("TokenDefs " + list)
                    list.map{case WithAction(tdef) => 
                                (processRegex(tdef._1, flags), tdef._2, processNewState(tdef._3, unprocessed, processed)) 
                            case WithoutAction(tdef) => 
                                (processRegex(tdef._1, flags), tdef._2, DoNothing)
                            }
                    }
            }
        }
    }

    private def processRegex(rex: String, flags: Int) = {
        (s: String) => Pattern.compile(rex, flags).matcher(s)
    }

    private def processNewState(newState: Action, 
                            unprocessed: Map[String, StateDef], 
                            processed: scala.collection.mutable.Map[String, List[((String) => Matcher, Desc, Action)]]) = {
        
        newState                                  
    }



    override def getTokensUnprocessed(text: String) =  getTokensUnprocessed(text, List("root"))

    /*
        Split ``text`` into (tokentype, text) pairs.

        ``stack`` is the inital stack (default: "['root']"
    */
    def getTokensUnprocessed(text: String, stack: List[String]) = {
        var pos = 0
        var tokendefs = _tokens
        var statestack = stack
        var statetokens = tokendefs(statestack.head)
        var result = new scala.collection.mutable.ListBuffer[(Int, Token, String)]()
        var shouldStop = false

        val len = text.length
        while(!shouldStop) {
            var added = false
            statetokens.map(t => (t._1(text), t._2, t._3))
                        .filter(_._1.region(pos, len).lookingAt)
                        .headOption
                        .foreach(t => {
                val (m, token, action) = t
                token match {
                    case IsToken(tt) => result += ((pos, tt, m.group()))
                    case ByGroups(tl @ Token*) => tl.zipWithIndex
                                .map(tt => (m.group(tt._2 + 1), tt._1))
                                .filter(!_._1.isEmpty)
                                .foreach(tt => result += ((pos, tt._2, tt._1)))
                }
                
   
                pos = m.end()
                statestack = action match {
                    case GoTo(state) => state :: statestack
                    case Pop(steps) => statestack.drop(steps)
                    case Pop => statestack.tail
                    case Push => statestack.head :: statestack
                    case DoNothing => statestack
                }
                println("Stack " + statestack)
                statetokens = tokendefs(statestack.head)
                added = true
            })
            if(!added) {
            try {
                if(text(pos) == '\n') {
                    // at EOL, reset state to "root"
                    pos += 1
                    statestack = List("root")
                    statetokens = tokendefs("root")
                    result += ((pos, Text, "\n"))
                } else if(statestack.isEmpty) {
                    result += ((pos, Error, text(pos).toString))
                    pos += 1
                }
            } catch {
                case e: IndexOutOfBoundsException => shouldStop = true
            }    
            }  
                          
        }
        result.toList          
    }
}

class RichTuple1(t: (String, Token)) {
    def >> (str: String) = WithAction((t._1, IsToken(t._2), GoTo(str)))
    def >> (a: Action) = WithAction((t._1, IsToken(t._2), a))
}

class RichTuple2(t: (String, ByGroups)) {
    def >> (str: String) = WithAction((t._1, t._2, GoTo(str)))
    def >> (a: Action) = WithAction((t._1, t._2, a))
}

object Helpers extends StateDefHelpers with DefinitionHelpers with ActionHelpers with DescHelpers {

    implicit def  Tuple2RichTuple(t: (String, Token)) = new RichTuple1(t)
    implicit def  Tuple2RichTuple(t: (String, ByGroups)) = new RichTuple2(t)

    val _default_analyse = (x: String) => 0.0
}