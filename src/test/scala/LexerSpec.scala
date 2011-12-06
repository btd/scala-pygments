import org.specs2.mutable._

import main._
import lexer._

class LexerSpec extends Specification {

    var sql = """SELECT * FROM TABLE WHERE a < 10 GROUP BY b HAVING b < 5 ORDER BY 1 desc"""

    /*"Sql Lexer" should {
      "parse sql" in {
        (new lexer.SqlLexer(new LexerOptions)).getTokens(sql) must contain((Keyword,"SELECT"), (Text," "), (Operator,"*"), (Text," "), (Keyword,"FROM"), (Text," "), (Keyword,"TABLE"), (Text," "), (Keyword,"WHERE"), (Text," "), (Name,"a"), (Text," "), (Operator,"<"), (Text," "), (Number.Integer,"10"), (Text," "), (Keyword,"GROUP"), (Text," "), (Keyword,"BY"), (Text," "), (Name,"b"), (Text," "), (Keyword,"HAVING"), (Text," "), (Name,"b"), (Text," "), (Operator,"<"), (Text," "), (Number.Integer,"5"), (Text," "), (Keyword,"ORDER"), (Text," "), (Keyword,"BY"), (Text," "), (Number.Integer,"1"), (Text," "), (Keyword,"desc"), (Text,"")).only.inOrder
      }
    }*/

    var scala = """
    package filter

import main._


/*
"Null" lexer, doesn't highlight anything.
*/
class TextLexer(val options: LexerOptions) extends Lexer {
  override val name = "Text only"

  override val aliases = List("text")

  override val filenames = List("*.txt")

  override val mimetypes = List("text/plain")

    override def getTokensUnprocessed(text: String) = List((0, Text, text))
}
    """

    "ScalaLexer" should {
      "parse scala code" in {
        val l = new lexer.ScalaLexer(new LexerOptions)
       l.getTokens(scala) === Nil
      }
    }
  }