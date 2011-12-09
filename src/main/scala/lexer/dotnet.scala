package lexer

import main._
import java.util.regex.Pattern
import Helpers._


/*
	For
    `Visual Basic.NET <http://msdn2.microsoft.com/en-us/vbasic/default.aspx>`_
    source code.
*/
class VbNetLexer(val options: LexerOptions) extends RegexLexer {
	override val name = "VB.net"

    override val aliases = "vb.net" :: "vbnet" :: Nil
    override val filenames = "*.vb" :: "*.bas" :: Nil
    override val mimetypes = "text/x-vbnet" :: "text/x-vba" :: Nil

    override val flags = Pattern.CASE_INSENSITIVE | Pattern.MULTILINE 


    val tokens = Map[String, StateDef](
        ("root", List[Definition](
            ("""^\s*<.*?>""", Name.Attribute),
            ("""\s+""", Text),
            ("""\n""", Text),
            ("""rem\b.*?\n""", Comment),
            ("'.*?\n", Comment),
            ("""#If\s.*?\sThen|#ElseIf\s.*?\sThen|#End\s+If|#Const|""" + 
             """#ExternalSource.*?\n|#End\s+ExternalSource|""" + 
             """#Region.*?\n|#End\s+Region|#ExternalChecksum""",
             Comment.Preproc),
            ("""[\(\){}!#,.:]""", Punctuation),
            ("""Option\s+(Strict|Explicit|Compare)\s+""" + 
             """(On|Off|Binary|Text)""", Keyword.Declaration),
            ("""(?<!\.)(AddHandler|Alias|""" + 
             """ByRef|ByVal|Call|Case|Catch|CBool|CByte|CChar|CDate|""" + 
             """CDec|CDbl|CInt|CLng|CObj|Continue|CSByte|CShort|""" + 
             """CSng|CStr|CType|CUInt|CULng|CUShort|Declare|""" + 
             """Default|Delegate|DirectCast|Do|Each|Else|ElseIf|""" + 
             """EndIf|Erase|Error|Event|Exit|False|Finally|For|""" + 
             """Friend|Get|Global|GoSub|GoTo|Handles|If|""" + 
             """Implements|Inherits|Interface|""" + 
             """Let|Lib|Loop|Me|MustInherit|""" + 
             """MustOverride|MyBase|MyClass|Narrowing|New|Next|""" + 
             """Not|Nothing|NotInheritable|NotOverridable|Of|On|""" + 
             """Operator|Option|Optional|Overloads|Overridable|""" + 
             """Overrides|ParamArray|Partial|Private|Protected|""" + 
             """Public|RaiseEvent|ReadOnly|ReDim|RemoveHandler|Resume|""" + 
             """Return|Select|Set|Shadows|Shared|Single|""" + 
             """Static|Step|Stop|SyncLock|Then|""" + 
             """Throw|To|True|Try|TryCast|Wend|""" + 
             """Using|When|While|Widening|With|WithEvents|""" + 
             """WriteOnly)\b""", Keyword),
            ("""(?<!\.)End\b""", Keyword) >> "end",
            ("""(?<!\.)(Dim|Const)\b""", Keyword) >> "dim",
            ("""(?<!\.)(Function|Sub|Property)(\s+)""",
             ByGroups(Keyword, Text)) >> "funcname",
            ("""(?<!\.)(Class|Structure|Enum)(\s+)""",
             ByGroups(Keyword, Text)) >>  "classname",
            ("""(?<!\.)(Module|Namespace|Imports)(\s+)""",
             ByGroups(Keyword, Text)) >>  "namespace",
            ("""(?<!\.)(Boolean|Byte|Char|Date|Decimal|Double|Integer|Long|""" +
             """Object|SByte|Short|Single|String|Variant|UInteger|ULong|""" +
             """UShort)\b""", Keyword.Type),
            ("""(?<!\.)(AddressOf|And|AndAlso|As|GetType|In|Is|IsNot|Like|Mod|""" +
             """Or|OrElse|TypeOf|Xor)\b""", Operator.Word),
            ("""&=|[*]=|/=|\\=|\^=|\+=|-=|<<=|>>=|<<|>>|:=|""" +
             """<=|>=|<>|[-&*/\\^+=<>]""",
             Operator),
            ("\"", Str) >> "string",
            ("""[a-zA-Z_][a-zA-Z0-9_]*[%&@!#$]?""", Name),
            ("""#.*?#""", Literal.Date),
            ("""(\d+\.\d*|\d*\.\d+)([fF][+-]?[0-9]+)?""", Number.Float),
            ("""\d+([SILDFR]|US|UI|UL)?""", Number.Integer),
            ("""&H[0-9a-f]+([SILDFR]|US|UI|UL)?""", Number.Integer),
            ("""&O[0-7]+([SILDFR]|US|UI|UL)?""", Number.Integer),
            ("""_\n""", Text) // Line continuation
        )),
        ("string", List[Definition](
            ("\"\"", Str),
            (""""C?""", Str) >> Pop,
            ("""[^"]+""", Str)
        )),
        ("dim", List[Definition](
            ("""[a-z_][a-z0-9_]*""", Name.Variable) >> Pop,
            ("""""", Text) >> Pop  // any other syntax
        )),
        ("funcname", List[Definition](
            ("""[a-z_][a-z0-9_]*""", Name.Function) >> Pop 
        )),
        ("classname", List[Definition](
            ("""[a-z_][a-z0-9_]*""", Name.Class) >> Pop 
        )),
        ("namespace", List[Definition](
            ("""[a-z_][a-z0-9_.]*""", Name.Namespace) >> Pop 
        )),
        ("end", List[Definition](
            ("""\s+""", Text),
            ("""(Function|Sub|Property|Class|Structure|Enum|Module|Namespace)\b""", Keyword) >> Pop,
            ("""""", Text) >> Pop 
        ))
    )
}
/*
Lexer for ASP.NET pages.
*/
class GenericAspxLexer(val options: LexerOptions) extends RegexLexer {
	override val name = "aspx-gen"

    override val aliases =  Nil
    override val filenames =  Nil
    override val mimetypes =  Nil

    override val flags = Pattern.DOTALL

	private val xml = new XmlLexer(options)

    val tokens = Map[String, StateDef](
        ("root", List[Definition](
            ("""(<%[@=#]?)(.*?)(%>)""", ByGroups(Name.Tag, Other, Name.Tag)),
            ("""(<script.*?>)(.*?)(</script>)""", ByGroups(Using(xml), Other, Using(xml))),
            ("""(.+?)(?=<)""", Using(xml)),
            (""".+""", Using(xml))
        ))
    )
}


/*
Lexer for highligting Visual Basic.net within ASP.NET pages.
*/
class VbNetAspxLexer(val options: LexerOptions) extends DelegatingLexer {
	def _root_lexer() = new VbNetLexer(options)

	def _language_lexer() = new GenericAspxLexer(options)

	override val name = "aspx-vb"

    override val aliases = "aspx-vb" :: Nil
    override val filenames = "*.aspx" :: "*.asax" :: "*.ascx" :: "*.ashx" :: "*.asmx" :: "*.axd" :: Nil
    override val mimetypes = Nil

}


        