package main


trait Token {

    val parent: Token

    override def toString = this.getClass.getSimpleName

    override def equals(that: Any) = that match { 
       case other: Token => other.toString == toString && other.toString == toString 
       case _ => false 
     }
}

object Token extends Token { val parent = null }

// Special token types
trait Text extends Token 
object Text extends Text {
    val parent = Token

    object Symbol extends Text {
        val parent = Text
    }
}
object Whitespace extends Text {
    val parent = Text
}
object Error extends Token { val parent = Token }

// Text that doesn"t belong to this lexer (e.g. HTML in PHP)
object Other extends Token { val parent = Token } 

// Common token types for source code
trait Keyword extends  Token 
object Keyword extends  Keyword {
    val parent = Token
   

	object Constant extends Keyword  { val parent = Keyword }
	object Declaration extends Keyword { val parent = Keyword }
	object Namespace extends Keyword { val parent = Keyword }
	object Pseudo extends Keyword { val parent = Keyword }
	object Reserved extends Keyword { val parent = Keyword }
	object Type extends Keyword  { val parent = Keyword }
}
trait Name         extends  Token 
object Name         extends  Name {
    val parent = Token

	object Attribute extends Name  { val parent = Name }

	trait Builtin extends Name

    object Builtin extends  Builtin {
        val parent = Name 

    	object Pseudo extends  Builtin { val parent = Builtin } 
    }
    
    object Class extends Name  { val parent = Name }
    object Constant extends Name  { val parent = Name }
    object Decorator extends Name  { val parent = Name }
    object Entity extends Name  { val parent = Name }
    object Exception extends Name  { val parent = Name }
    object Function extends Name  { val parent = Name }
    object Property extends Name  { val parent = Name }
    object Label extends Name  { val parent = Name }
    object Namespace extends Name  { val parent = Name }
    object Other extends Name  { val parent = Name }
    object Tag extends Name  { val parent = Name }
    trait Variable extends  Name 
    object Variable extends  Variable {
        val parent = Name

    	object Class  extends  Variable { val parent = Variable }
    	object Global  extends  Variable { val parent = Variable }
    	object Instance  extends  Variable { val parent = Variable }
    }
    
}

trait  Literal      extends  Token
object Literal      extends  Literal {
    val parent = Token

	object Date  extends  Literal { val parent = Literal }
}
trait Str       extends  Literal
object Str       extends  Str {
    val parent = Literal

	object Backtick extends Str  { val parent = Str }
    object Char extends Str  { val parent = Str }
    object Doc extends Str  { val parent = Str }
    object Double extends Str  { val parent = Str }
    object Escape extends Str  { val parent = Str }
    object Heredoc extends Str  { val parent = Str }
    object Interpol extends Str  { val parent = Str }
    object Other extends Str  { val parent = Str }
    object Regex extends Str  { val parent = Str }
    object Single extends Str  { val parent = Str }
    object Symbol extends Str  { val parent = Str }
}
trait Number       extends  Literal
object Number       extends  Number {
    val parent = Literal

	object Float extends Number  { val parent = Number }
    object Hex extends Number  { val parent = Number }
    trait Integer extends Number 
    object Integer extends  Integer {
        val parent = Number

    	object Long extends  Integer { val parent = Integer }
    }    
    object Oct extends Number  { val parent = Number }
}
object Punctuation  extends  Token { val parent = Token }
trait Operator     extends  Token 
object Operator     extends  Operator {
    val parent = Token

	object Word extends  Operator { val parent = Operator }
}
trait Comment      extends  Token
object Comment      extends  Comment {
    val parent = Token

	object Multiline extends  Comment { val parent = Comment }
    object Preproc extends  Comment { val parent = Comment }
    object Single extends  Comment { val parent = Comment }
    object Special extends  Comment { val parent = Comment }
}

// Generic types for non-source code
trait Generic      extends  Token 
object Generic      extends  Generic {
    val parent = Token

	object Deleted extends  Generic { val parent = Generic }
    object Emph extends  Generic { val parent = Generic }
    object Error extends  Generic { val parent = Generic }
    object Heading extends  Generic { val parent = Generic }
    object Inserted extends  Generic { val parent = Generic }
    object Output extends  Generic { val parent = Generic }
    object Prompt extends  Generic { val parent = Generic }
    object Strong extends  Generic { val parent = Generic }
    object Subheading extends  Generic { val parent = Generic }
    object Traceback extends  Generic { val parent = Generic }
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
