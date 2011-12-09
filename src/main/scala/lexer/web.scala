package lexer

import main._
import java.util.regex.Pattern
import Helpers._


/*
Generic lexer for XML (eXtensible Markup Language).
*/
class XmlLexer(val options: LexerOptions) extends RegexLexer {
	override val name = "XML"

    override val aliases = "xml" :: Nil
    override val filenames = "*.xml" :: "*.xsl" :: "*.rss" :: "*.xslt" :: "*.xsd" :: "*.wsdl" :: Nil
    override val mimetypes = "text/xml" :: "application/xml" :: "image/svg+xml" :: "application/rss+xml" :: "application/atom+xml" :: Nil

    override val flags = Pattern.DOTALL | Pattern.MULTILINE

    val tokens = Map[String, StateDef](
        ("root", List[Definition](
            ("""[^<&]+""", Text),
            ("""&\S*?;""", Name.Entity),
            ("""\<\!\[CDATA\[.*?\]\]\>""", Comment.Preproc),
            ("""<!--""", Comment) >> "comment",
            ("""<\?.*?\?>""", Comment.Preproc),
            ("""<![^>]*>""", Comment.Preproc),
            ("""<\s*[a-zA-Z0-9:._-]+""", Name.Tag) >> "tag",
            ("""<\s*/\s*[a-zA-Z0-9:._-]+\s*>""", Name.Tag)
        )),
        ("comment", List[Definition](
            ("""[^-]+""", Comment),
            ("""-->""", Comment) >> Pop,
            ("""-""", Comment)
        )),
        ("tag", List[Definition](
            ("""\s+""", Text),
            ("""[a-zA-Z0-9_.:-]+\s*=""", Name.Attribute) >> "attr",
            ("""/?\s*>""", Name.Tag) >> Pop
        )),
        ("attr", List[Definition](
            ("""\s+""", Text),
            ("\".*?\"", Str) >> Pop,
            ("""'.*?'""", Str) >> Pop,
            ("""[^\s>]+""", Str) >> Pop
        ))
    )
}

/*
For ActionScript 3 source code.
*/
class ActionScript3Lexer(val options: LexerOptions) extends RegexLexer {
    override val name = "ActionScript 3"

    override val aliases = "as3" :: "actionscript3" :: Nil
    override val filenames = "*.as" :: Nil
    override val mimetypes = "application/x-actionscript" :: "text/x-actionscript" :: "text/actionscript" :: Nil

    override val flags = Pattern.DOTALL | Pattern.MULTILINE

	val identifier = """[$a-zA-Z_][a-zA-Z0-9_]*"""
    val typeidentifier = identifier + """(?:\.<\w+>)?"""

    val tokens = Map[String, StateDef](
        ("root", List[Definition](
            ("""\s+""", Text),
            ("""(function\s+)(""" + identifier + """)(\s*)(\()""",
             ByGroups(Keyword.Declaration, Name.Function, Text, Operator)) >> "funcparams",
            ("""(var|const)(\s+)(""" + identifier + """)(\s*)(:)(\s*)(""" +
             typeidentifier + """)""",
             ByGroups(Keyword.Declaration, Text, Name, Text, Punctuation, Text, Keyword.Type)),
            ("""(import|package)(\s+)((?:""" + identifier + """|\.)+)(\s*)""",
             ByGroups(Keyword, Text, Name.Namespace, Text)),
            ("""(new)(\s+)(""" + typeidentifier + """)(\s*)(\()""",
             ByGroups(Keyword, Text, Keyword.Type, Text, Operator)),
            ("""//.*?\n""", Comment.Single),
            ("""/\*.*?\*/""", Comment.Multiline),
            ("""/(\\\\|\\/|[^\n])*/[gisx]*""", Str.Regex),
            ("""(\.)(""" + identifier + """)""", ByGroups(Operator, Name.Attribute)),
            ("""(case|default|for|each|in|while|do|break|return|continue|if|else|""" +
             """throw|try|catch|with|new|typeof|arguments|instanceof|this|""" +
             """switch|import|include|as|is)\b""",
             Keyword),
            ("""(class|public|final|internal|native|override|private|protected|""" +
             """static|import|extends|implements|interface|intrinsic|return|super|""" +
             """dynamic|function|const|get|namespace|package|set)\b""",
             Keyword.Declaration),
            ("""(true|false|null|NaN|Infinity|-Infinity|undefined|void)\b""",
             Keyword.Constant),
            ("""(decodeURI|decodeURIComponent|encodeURI|escape|eval|isFinite|isNaN|""" +
             """isXMLName|clearInterval|fscommand|getTimer|getURL|getVersion|""" +
             """isFinite|parseFloat|parseInt|setInterval|trace|updateAfterEvent|""" +
             """unescape)\b""", Name.Function),
            (identifier, Name),
            ("""[0-9][0-9]*\.[0-9]+([eE][0-9]+)?[fd]?""", Number.Float),
            ("""0x[0-9a-f]+""", Number.Hex),
            ("""[0-9]+""", Number.Integer),
            (""""(\\\\|\\"|[^"])*"""", Str.Double),
            ("'(\\\\|\\'|[^'])*'", Str.Single),
            ("""[~\^\*!%&<>\|+=:;,/?\\{}\[\]();.-]+""", Operator)
        )),
        ("funcparams", List[Definition](
            ("""\s+""", Text),
            ("""(\s*)(\.\.\.)?(""" + identifier + """)(\s*)(:)(\s*)(""" +
             typeidentifier + """|\*)(\s*)""",
             ByGroups(Text, Punctuation, Name, Text, Operator, Text,
                      Keyword.Type, Text)) >> "defval",
            ("""\)""", Operator) >> "type"
        )),
        ("type", List[Definition](
            ("""(\s*)(:)(\s*)(""" + typeidentifier + """|\*)""",
             ByGroups(Text, Operator, Text, Keyword.Type)) >> Pop(2),
            ("""\s*""", Text) >> Pop(2)
        )),
        ("defval", List[Definition](
            ("""(=)(\s*)([^(),]+)(\s*)(,?)""",
             ByGroups(Operator, Text, Using(this), Text, Operator)) >> Pop,
            (""",?""", Operator) >> Pop
        ))
    )
}
