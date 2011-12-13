package main


object Main extends App {
	val code = """
<cfif thisTag.executionMode EQ "start">
 <cfdirectory action="list" name="getImages" directory="#ExpandPath(ATTRIBUTES.directory)#">
 
 <cfparam name="SESSION.picture" default="0">
 <cfset SESSION.picture = SESSION.picture + 1>
 
 <cfif SESSION.picture GT getImages.RecordCount>
  <cfset SESSION.picture = 1>
 </cfif>
 
 <cfoutput> 
 <img src="#ATTRIBUTES.directory#/#getImages.name[SESSION.picture]#"
  <cfif isDefined("ATTRIBUTES.height")>
   height="#ATTRIBUTES.height#"
  </cfif>
  <cfif isDefined("ATTRIBUTES.width")>
   width="#ATTRIBUTES.width#"
  </cfif>
  <cfif isDefined("ATTRIBUTES.alt")>
   alt="#ATTRIBUTES.alt#"
  <cfelse>
   alt="#getImages.name[SESSION.picture]#"
  </cfif>>
 </cfoutput>
<cfelseif thisTag.executionMode EQ "end">
 <cfif isDefined("ATTRIBUTES.CommentBody") AND ATTRIBUTES.CommentBody EQ "true">
  <cfset thisTag.GeneratedContent = "<!--#thisTag.GeneratedContent#-->">
 </cfif>
</cfif> 
"""
	var out_file = new java.io.FileOutputStream("test.html")
	var out_stream = new java.io.PrintStream(out_file)

	//Pygments.lex(code, new lexer.ScalaLexer(new LexerOptions ) )
	out_stream.println("""<link type="text/css" rel="stylesheet" href="test.css">""")
	Pygments.highlight(code, new lexer.ColdfusionHtmlLexer(new LexerOptions ), new formatters.HtmlFormatter )
		.foreach(t => out_stream.println(t))
		

	out_stream.close

	/*var out_file1 = new java.io.FileOutputStream("test.css")

	var out_stream1 = new java.io.PrintStream(out_file1)

	out_stream1.print((new formatters.HtmlFormatter(new FormatterOptions(new styles.MurphyStyle )) ).styleDefs)

	out_stream1.close*/
}