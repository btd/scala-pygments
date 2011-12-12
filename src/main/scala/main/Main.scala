package main


object Main extends App {
	val code = """
+++++++++++++++++++++++++++++++++++++++++++++
 +++++++++++++++++++++++++++.+++++++++++++++++
 ++++++++++++.+++++++..+++.-------------------
 ---------------------------------------------
 ---------------.+++++++++++++++++++++++++++++
 ++++++++++++++++++++++++++.++++++++++++++++++
 ++++++.+++.------.--------.------------------
 ---------------------------------------------
 ----.-----------------------.
"""
	var out_file = new java.io.FileOutputStream("test.html")
	var out_stream = new java.io.PrintStream(out_file)

	//Pygments.lex(code, new lexer.ScalaLexer(new LexerOptions ) )
	out_stream.println("""<link type="text/css" rel="stylesheet" href="test.css">""")
	Pygments.highlight(code, new lexer.BrainfuckLexer(new LexerOptions ), new formatters.HtmlFormatter )
		.foreach(t => out_stream.println(t))
		

	out_stream.close

	/*var out_file1 = new java.io.FileOutputStream("test.css")

	var out_stream1 = new java.io.PrintStream(out_file1)

	out_stream1.print((new formatters.HtmlFormatter(new FormatterOptions(new styles.MurphyStyle )) ).styleDefs)

	out_stream1.close*/
}