package main


object Main extends App {
	val code = """
(defn run [nvecs nitems nthreads niters]
  (let [vec-refs (vec (map (comp ref vec)
                           (partition nitems (range (* nvecs nitems)))))
        swap #(let [v1 (rand-int nvecs)
                    v2 (rand-int nvecs)
                    i1 (rand-int nitems)
                    i2 (rand-int nitems)]
                (dosync
                 (let [temp (nth @(vec-refs v1) i1)]
                   (alter (vec-refs v1) assoc i1 (nth @(vec-refs v2) i2))
                   (alter (vec-refs v2) assoc i2 temp))))
        report #(do
                 (prn (map deref vec-refs))
                 (println "Distinct:"
                          (count (distinct (apply concat (map deref vec-refs))))))]
    (report)
    (dorun (apply pcalls (repeat nthreads #(dotimes [_ niters] (swap)))))
    (report)))
 
(run 100 10 10 100000)
"""
	var out_file = new java.io.FileOutputStream("test.html")
	var out_stream = new java.io.PrintStream(out_file)

	//Pygments.lex(code, new lexer.ScalaLexer(new LexerOptions ) )
	out_stream.println("""<link type="text/css" rel="stylesheet" href="test.css">""")
	Pygments.highlight(code, new lexer.ClojureLexer(new LexerOptions ), new formatters.HtmlFormatter )
		.foreach(t => out_stream.println(t))
		

	out_stream.close

	/*var out_file1 = new java.io.FileOutputStream("test.css")

	var out_stream1 = new java.io.PrintStream(out_file1)

	out_stream1.print((new formatters.HtmlFormatter(new FormatterOptions(new styles.MurphyStyle )) ).styleDefs)

	out_stream1.close*/
}