package main

object Pygments {

	def lex(code: String, lexer: Lexer) = lexer.getTokens(code)

	def format(tokens: List[(Token, String)], formatter: Formatter) = {
		try {
			formatter.format(tokens)
		} catch {
			case _ => {
				
			}
		}
	}
	    


	def highlight(code: String, 
				lexer: Lexer, 
				formatter: Formatter) = format(lex(code, lexer), formatter)
}