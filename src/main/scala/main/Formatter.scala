package main

import styles._

class FormatterOptions(
	val style: Style = new DefaultStyle,
	val full: Boolean = false,
	val title: String = ""
)

/*
    Converts a token stream to text.

    Options accepted:

    "style"
        The style to use, can be a string or a Style subclass
        (default: "default"). Not used by e.g. the
        TerminalFormatter.
    "full"
        Tells the formatter to output a "full" document, i.e.
        a complete self-contained document. This doesn't have
        any effect for some formatters (default: false).
    "title"
        If "full" is true, the title that should be used to
        caption the document (default: '').
    "encoding"
        If given, must be an encoding name. This will be used to
        convert the Unicode token strings to byte strings in the
        output. If it is "" or None, Unicode strings will be written
        to the output file, which most file-like objects do not
        support (default: None).
    "outencoding"
        Overrides "encoding" if given.
*/
trait Formatter {

	val options: FormatterOptions    

    //: Name of the formatter
    val name: String

    //: Shortcuts for the formatter
    val aliases: List[String]

    //: fn match rules
    val filenames: List[String]

    //: If True, this formatter outputs Unicode strings when no encoding
    //: option is given.
    val unicodeoutput = true

    /*
        Format "tokensource", an iterable of "(tokentype, tokenstring)"
        tuples and write it into "outfile".
    */
    def format(tokensource: List[(Token, String)]): List[String]

    def styleDefs: String
}