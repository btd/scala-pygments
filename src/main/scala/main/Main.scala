package main


object Main extends App {
	val code = """
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
	var out_file = new java.io.FileOutputStream("test.html")
	var out_stream = new java.io.PrintStream(out_file)

	//Pygments.lex(code, new lexer.ScalaLexer(new LexerOptions ) )

	Pygments.highlight(code, new lexer.ScalaLexer(new LexerOptions), new formatters.HtmlFormatter )
		.foreach(t => out_stream.println(t))
		

	out_stream.close

	var out_file1 = new java.io.FileOutputStream("test.css")

	var out_stream1 = new java.io.PrintStream(out_file1)

	out_stream1.print((new formatters.HtmlFormatter ).styleDefs)

	out_stream1.close
}