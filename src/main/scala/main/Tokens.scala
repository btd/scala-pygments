package main


trait Token {
    override def toString = this.getClass.getSimpleName

}

object Token extends Token

// Special token types
trait Text extends Token
object Text extends Text {
    object Symbol extends Text
}
object Whitespace extends Text
object Error extends Token

// Text that doesn"t belong to this lexer (e.g. HTML in PHP)
object Other extends Token

// Common token types for source code
trait Keyword extends  Token
object Keyword extends  Keyword {

	object Constant extends Keyword
	object Declaration extends Keyword
	object Namespace extends Keyword
	object Pseudo extends Keyword
	object Reserved extends Keyword
	object Type extends Keyword
}
trait Name         extends  Token
object Name         extends  Token {
	object Attribute extends  Name

	trait Builtin extends  Name

    object Builtin extends  Builtin {
    	object Pseudo extends  Builtin
    }
    
    object Class extends  Name
    object Constant extends  Name
    object Decorator extends  Name
    object Entity extends  Name
    object Exception extends  Name
    object Function extends  Name
    object Property extends  Name
    object Label extends  Name
    object Namespace extends  Name
    object Other extends  Name
    object Tag extends  Name
    trait Variable extends  Name
    object Variable extends  Variable {
    	object Class  extends  Variable
    	object Global  extends  Variable
    	object Instance  extends  Variable
    }
    
}

trait  Literal      extends  Token
object Literal      extends  Literal {
	object Date  extends  Literal
}
trait Str       extends  Literal
object Str       extends  Str {
	object Backtick extends  Str
    object Char extends  Str
    object Doc extends  Str
    object Double extends  Str
    object Escape extends  Str
    object Heredoc extends  Str
    object Interpol extends  Str
    object Other extends  Str
    object Regex extends  Str
    object Single extends  Str
    object Symbol extends  Str
}
trait Number       extends  Literal
object Number       extends  Number {
	object Float extends  Number
    object Hex extends  Number
    trait Integer extends  Number
    object Integer extends  Integer {
    	object Long extends  Integer
    }    
    object Oct extends  Number
}
object Punctuation  extends  Token
trait Operator     extends  Token
object Operator     extends  Operator {
	object Word extends  Operator
}
trait Comment      extends  Token
object Comment      extends  Comment {
	object Multiline extends  Comment
    object Preproc extends  Comment
    object Single extends  Comment
    object Special extends  Comment
}

// Generic types for non-source code
trait Generic      extends  Token 
object Generic      extends  Generic {
	object Deleted extends  Generic
    object Emph extends  Generic
    object Error extends  Generic
    object Heading extends  Generic
    object Inserted extends  Generic
    object Output extends  Generic
    object Prompt extends  Generic
    object Strong extends  Generic
    object Subheading extends  Generic
    object Traceback extends  Generic
}

object Tokens {
// Map standard token types to short names, used in CSS class naming.
// If you add a new item, please be sure to run this file to perform
// a consistency check for duplicate values.
val STANDARD_TYPES: Map[Token, String] = List(
    Token ->                         "",

    Text ->                          "",
    Whitespace ->                    "w",
    Error ->                         "err",
    Other ->                         "x",

    Keyword ->                       "k",
    Keyword.Constant ->              "kc",
    Keyword.Declaration ->           "kd",
    Keyword.Namespace ->             "kn",
    Keyword.Pseudo ->                "kp",
    Keyword.Reserved ->              "kr",
    Keyword.Type ->                  "kt",

    Name ->                          "n",
    Name.Attribute ->                "na",
    Name.Builtin ->                  "nb",
    Name.Builtin.Pseudo ->           "bp",
    Name.Class ->                    "nc",
    Name.Constant ->                 "no",
    Name.Decorator ->                "nd",
    Name.Entity ->                   "ni",
    Name.Exception ->                "ne",
    Name.Function ->                 "nf",
    Name.Property ->                 "py",
    Name.Label ->                    "nl",
    Name.Namespace ->                "nn",
    Name.Other ->                    "nx",
    Name.Tag ->                      "nt",
    Name.Variable ->                 "nv",
    Name.Variable.Class ->           "vc",
    Name.Variable.Global ->          "vg",
    Name.Variable.Instance ->        "vi",

    Literal ->                       "l",
    Literal.Date ->                  "ld",

    Str ->                        "s",
    Str.Backtick ->               "sb",
    Str.Char ->                   "sc",
    Str.Doc ->                    "sd",
    Str.Double ->                 "s2",
    Str.Escape ->                 "se",
    Str.Heredoc ->                "sh",
    Str.Interpol ->               "si",
    Str.Other ->                  "sx",
    Str.Regex ->                  "sr",
    Str.Single ->                 "s1",
    Str.Symbol ->                 "ss",

    Number ->                        "m",
    Number.Float ->                  "mf",
    Number.Hex ->                    "mh",
    Number.Integer ->                "mi",
    Number.Integer.Long ->           "il",
    Number.Oct ->                    "mo",

    Operator ->                      "o",
    Operator.Word ->                 "ow",

    Punctuation ->                   "p",

    Comment ->                       "c",
    Comment.Multiline ->             "cm",
    Comment.Preproc ->               "cp",
    Comment.Single ->                "c1",
    Comment.Special ->               "cs",

    Generic ->                       "g",
    Generic.Deleted ->               "gd",
    Generic.Emph ->                  "ge",
    Generic.Error ->                 "gr",
    Generic.Heading ->               "gh",
    Generic.Inserted ->              "gi",
    Generic.Output ->                "go",
    Generic.Prompt ->                "gp",
    Generic.Strong ->                "gs",
    Generic.Subheading ->            "gu",
    Generic.Traceback ->             "gt").toMap
}
