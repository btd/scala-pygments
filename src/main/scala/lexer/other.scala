package lexer

import main._
import java.util.regex.Pattern
import Helpers._

/*
    Lexer for Structured Query Language. Currently, this lexer does
    not recognize any special syntax except ANSI SQL.
*/
class SqlLexer(val options: LexerOptions) extends RegexLexer {
    
    override val name = "SQL"

    override val aliases = "sql" :: Nil
    override val filenames = "*.sql" :: Nil
    override val mimetypes = "text/x-sql" :: Nil

    override val flags = Pattern.CASE_INSENSITIVE

    val tokens = Map[String, StateDef](
        ("root", List[Definition](
            ("""\s+""", Text) ,
            ("""--.*?\n""", Comment.Single) ,
            ("""/\*""", Comment.Multiline) >> "multiline-comments" , 
            (("""(ABORT|ABS|ABSOLUTE|ACCESS|ADA|ADD|ADMIN|AFTER|AGGREGATE|""" + 
             """ALIAS|ALL|ALLOCATE|ALTER|ANALYSE|ANALYZE|AND|ANY|ARE|AS|""" + 
             """ASC|ASENSITIVE|ASSERTION|ASSIGNMENT|ASYMMETRIC|AT|ATOMIC|""" + 
             """AUTHORIZATION|AVG|BACKWARD|BEFORE|BEGIN|BETWEEN|BITVAR|""" + 
             """BIT_LENGTH|BOTH|BREADTH|BY|C|CACHE|CALL|CALLED|CARDINALITY|""" + 
             """CASCADE|CASCADED|CASE|CAST|CATALOG|CATALOG_NAME|CHAIN|""" + 
             """CHARACTERISTICS|CHARACTER_LENGTH|CHARACTER_SET_CATALOG|""" + 
             """CHARACTER_SET_NAME|CHARACTER_SET_SCHEMA|CHAR_LENGTH|CHECK|""" + 
             """CHECKED|CHECKPOINT|CLASS|CLASS_ORIGIN|CLOB|CLOSE|CLUSTER|""" + 
             """COALSECE|COBOL|COLLATE|COLLATION|COLLATION_CATALOG|""" + 
             """COLLATION_NAME|COLLATION_SCHEMA|COLUMN|COLUMN_NAME|""" + 
             """COMMAND_FUNCTION|COMMAND_FUNCTION_CODE|COMMENT|COMMIT|""" + 
             """COMMITTED|COMPLETION|CONDITION_NUMBER|CONNECT|CONNECTION|""" + 
             """CONNECTION_NAME|CONSTRAINT|CONSTRAINTS|CONSTRAINT_CATALOG|""" + 
             """CONSTRAINT_NAME|CONSTRAINT_SCHEMA|CONSTRUCTOR|CONTAINS|""" + 
             """CONTINUE|CONVERSION|CONVERT|COPY|CORRESPONTING|COUNT|""" + 
             """CREATE|CREATEDB|CREATEUSER|CROSS|CUBE|CURRENT|CURRENT_DATE|""" + 
             """CURRENT_PATH|CURRENT_ROLE|CURRENT_TIME|CURRENT_TIMESTAMP|""" + 
             """CURRENT_USER|CURSOR|CURSOR_NAME|CYCLE|DATA|DATABASE|""" + 
             """DATETIME_INTERVAL_CODE|DATETIME_INTERVAL_PRECISION|DAY|""" + 
             """DEALLOCATE|DECLARE|DEFAULT|DEFAULTS|DEFERRABLE|DEFERRED|""" + 
             """DEFINED|DEFINER|DELETE|DELIMITER|DELIMITERS|DEREF|DESC|""" + 
             """DESCRIBE|DESCRIPTOR|DESTROY|DESTRUCTOR|DETERMINISTIC|""" + 
             """DIAGNOSTICS|DICTIONARY|DISCONNECT|DISPATCH|DISTINCT|DO|""" + 
             """DOMAIN|DROP|DYNAMIC|DYNAMIC_FUNCTION|DYNAMIC_FUNCTION_CODE|""" + 
             """EACH|ELSE|ENCODING|ENCRYPTED|END|END-EXEC|EQUALS|ESCAPE|EVERY|""" + 
             """EXCEPT|ESCEPTION|EXCLUDING|EXCLUSIVE|EXEC|EXECUTE|EXISTING|""" + 
             """EXISTS|EXPLAIN|EXTERNAL|EXTRACT|FALSE|FETCH|FINAL|FIRST|FOR|""" + 
             """FORCE|FOREIGN|FORTRAN|FORWARD|FOUND|FREE|FREEZE|FROM|FULL|""" + 
             """FUNCTION|G|GENERAL|GENERATED|GET|GLOBAL|GO|GOTO|GRANT|GRANTED|""" + 
             """GROUP|GROUPING|HANDLER|HAVING|HIERARCHY|HOLD|HOST|IDENTITY|""" + 
             """IGNORE|ILIKE|IMMEDIATE|IMMUTABLE|IMPLEMENTATION|IMPLICIT|IN|""" + 
             """INCLUDING|INCREMENT|INDEX|INDITCATOR|INFIX|INHERITS|INITIALIZE|""" + 
             """INITIALLY|INNER|INOUT|INPUT|INSENSITIVE|INSERT|INSTANTIABLE|""" + 
             """INSTEAD|INTERSECT|INTO|INVOKER|IS|ISNULL|ISOLATION|ITERATE|JOIN|""" + 
             """KEY|KEY_MEMBER|KEY_TYPE|LANCOMPILER|LANGUAGE|LARGE|LAST|""" + 
             """LATERAL|LEADING|LEFT|LENGTH|LESS|LEVEL|LIKE|LIMIT|LISTEN|LOAD|""" + 
             """LOCAL|LOCALTIME|LOCALTIMESTAMP|LOCATION|LOCATOR|LOCK|LOWER|""" + 
             """MAP|MATCH|MAX|MAXVALUE|MESSAGE_LENGTH|MESSAGE_OCTET_LENGTH|""" + 
             """MESSAGE_TEXT|METHOD|MIN|MINUTE|MINVALUE|MOD|MODE|MODIFIES|""" + 
             """MODIFY|MONTH|MORE|MOVE|MUMPS|NAMES|NATIONAL|NATURAL|NCHAR|""" + 
             """NCLOB|NEW|NEXT|NO|NOCREATEDB|NOCREATEUSER|NONE|NOT|NOTHING|""" + 
             """NOTIFY|NOTNULL|NULL|NULLABLE|NULLIF|OBJECT|OCTET_LENGTH|OF|OFF|""" + 
             """OFFSET|OIDS|OLD|ON|ONLY|OPEN|OPERATION|OPERATOR|OPTION|OPTIONS|""" + 
             """OR|ORDER|ORDINALITY|OUT|OUTER|OUTPUT|OVERLAPS|OVERLAY|OVERRIDING|""" + 
             """OWNER|PAD|PARAMETER|PARAMETERS|PARAMETER_MODE|PARAMATER_NAME|""" + 
             """PARAMATER_ORDINAL_POSITION|PARAMETER_SPECIFIC_CATALOG|""" + 
             """PARAMETER_SPECIFIC_NAME|PARAMATER_SPECIFIC_SCHEMA|PARTIAL|""" + 
             """PASCAL|PENDANT|PLACING|PLI|POSITION|POSTFIX|PRECISION|PREFIX|""" + 
             """PREORDER|PREPARE|PRESERVE|PRIMARY|PRIOR|PRIVILEGES|PROCEDURAL|""" + 
             """PROCEDURE|PUBLIC|READ|READS|RECHECK|RECURSIVE|REF|REFERENCES|""" + 
             """REFERENCING|REINDEX|RELATIVE|RENAME|REPEATABLE|REPLACE|RESET|""" + 
             """RESTART|RESTRICT|RESULT|RETURN|RETURNED_LENGTH|""" + 
             """RETURNED_OCTET_LENGTH|RETURNED_SQLSTATE|RETURNS|REVOKE|RIGHT|""" + 
             """ROLE|ROLLBACK|ROLLUP|ROUTINE|ROUTINE_CATALOG|ROUTINE_NAME|""" + 
             """ROUTINE_SCHEMA|ROW|ROWS|ROW_COUNT|RULE|SAVE_POINT|SCALE|SCHEMA|""" + 
             """SCHEMA_NAME|SCOPE|SCROLL|SEARCH|SECOND|SECURITY|SELECT|SELF|""" + 
             """SENSITIVE|SERIALIZABLE|SERVER_NAME|SESSION|SESSION_USER|SET|""" + 
             """SETOF|SETS|SHARE|SHOW|SIMILAR|SIMPLE|SIZE|SOME|SOURCE|SPACE|""" + 
             """SPECIFIC|SPECIFICTYPE|SPECIFIC_NAME|SQL|SQLCODE|SQLERROR|""" + 
             """SQLEXCEPTION|SQLSTATE|SQLWARNINIG|STABLE|START|STATE|STATEMENT|""" + 
             """STATIC|STATISTICS|STDIN|STDOUT|STORAGE|STRICT|STRUCTURE|STYPE|""" + 
             """SUBCLASS_ORIGIN|SUBLIST|SUBSTRING|SUM|SYMMETRIC|SYSID|SYSTEM|""" + 
             """SYSTEM_USER|TABLE|TABLE_NAME| TEMP|TEMPLATE|TEMPORARY|TERMINATE|""" + 
             """THAN|THEN|TIMESTAMP|TIMEZONE_HOUR|TIMEZONE_MINUTE|TO|TOAST|""" + 
             """TRAILING|TRANSATION|TRANSACTIONS_COMMITTED|""" + 
             """TRANSACTIONS_ROLLED_BACK|TRANSATION_ACTIVE|TRANSFORM|""" + 
             """TRANSFORMS|TRANSLATE|TRANSLATION|TREAT|TRIGGER|TRIGGER_CATALOG|""" + 
             """TRIGGER_NAME|TRIGGER_SCHEMA|TRIM|TRUE|TRUNCATE|TRUSTED|TYPE|""" + 
             """UNCOMMITTED|UNDER|UNENCRYPTED|UNION|UNIQUE|UNKNOWN|UNLISTEN|""" + 
             """UNNAMED|UNNEST|UNTIL|UPDATE|UPPER|USAGE|USER|""" + 
             """USER_DEFINED_TYPE_CATALOG|USER_DEFINED_TYPE_NAME|""" + 
             """USER_DEFINED_TYPE_SCHEMA|USING|VACUUM|VALID|VALIDATOR|VALUES|""" + 
             """VARIABLE|VERBOSE|VERSION|VIEW|VOLATILE|WHEN|WHENEVER|WHERE|""" + 
             """WITH|WITHOUT|WORK|WRITE|YEAR|ZONE)\b"""), Keyword) ,
            (("""(ARRAY|BIGINT|BINARY|BIT|BLOB|BOOLEAN|CHAR|CHARACTER|DATE|""" + 
             """DEC|DECIMAL|FLOAT|INT|INTEGER|INTERVAL|NUMBER|NUMERIC|REAL|""" + 
             """SERIAL|SMALLINT|VARCHAR|VARYING|INT8|SERIAL8|TEXT)\b"""),
             Name.Builtin) ,
            ("""[+*/<>=~!@#%^&|`?^-]""", Operator) ,
            ("""[0-9]+""", Number.Integer) ,
            // TODO: Backslash escapes?
            ("""'(''|[^'])*'""", Str.Single) ,
            ("\"(\"\"|[^\"])*\"", Str.Symbol) , // not a real string literal in ANSI SQL
            ("""[a-zA-Z_][a-zA-Z0-9_]*""", Name) ,
            ("""[;:()\[\],\.]""", Punctuation))),
        ("multiline-comments" , List[Definition](
            ("""/\*""", Comment.Multiline) >>  "multiline-comments",
            ("""\*/""", Comment.Multiline) >> Pop,
            ("""[^/\*]+""", Comment.Multiline) ,
            ("""[/*]""", Comment.Multiline))))

}

/*
    For `AppleScript source code
    <http://developer.apple.com/documentation/AppleScript/
    Conceptual/AppleScriptLangGuide>`_,
    including `AppleScript Studio
    <http://developer.apple.com/documentation/AppleScript/
    Reference/StudioReference>`_.
    Contributed by Andreas Amann <aamann@mac.com>.
*/
class AppleScriptLexer(val options: LexerOptions) extends RegexLexer {
    override val name = "AppleScript"

    override val aliases = "applescript" :: Nil
    override val filenames = "*.applescript" :: Nil
    override val mimetypes = Nil

    override val flags = Pattern.MULTILINE | Pattern.DOTALL

    val Identifiers = """[a-zA-Z]\w*"""
    val Literals = List[String]("AppleScript", "current application", "false", "linefeed",
                "missing value", "pi", "quote", "result", "return", "space",
                "tab", "text item delimiters", "true", "version")
    val Classes = List[String]("alias ", "application ", "boolean ", "class ", "constant ",
               "date ", "file ", "integer ", "list ", "number ", "POSIX file ",
               "real ", "record ", "reference ", "RGB color ", "script ",
               "text ", "unit types", "(Unicode )?text", "string")
    val BuiltIn = List[String]("attachment", "attribute run", "character", "day", "month",
               "paragraph", "word", "year")
    val HandlerParams = List[String]("about", "above", "against", "apart from", "around",
                     "aside from", "at", "below", "beneath", "beside",
                     "between", "for", "given", "instead of", "on", "onto",
                     "out of", "over", "since")
    val Commands = List[String]("ASCII (character|number)", "activate", "beep", "choose URL",
                "choose application", "choose color", "choose file( name)?",
                "choose folder", "choose from list",
                "choose remote application", "clipboard info",
                "close( access)?", "copy", "count", "current date", "delay",
                "delete", "display (alert|dialog)", "do shell script",
                "duplicate", "exists", "get eof", "get volume settings",
                "info for", "launch", "list (disks|folder)", "load script",
                "log", "make", "mount volume", "new", "offset",
                "open( (for access|location))?", "path to", "print", "quit",
                "random number", "read", "round", "run( script)?",
                "say", "scripting components",
                "set (eof|the clipboard to|volume)", "store script",
                "summarize", "system attribute", "system info",
                "the clipboard", "time to GMT", "write", "quoted form")
    val References = List[String]("(in )?back of", "(in )?front of", "[0-9]+(st|nd|rd|th)",
                  "first", "second", "third", "fourth", "fifth", "sixth",
                  "seventh", "eighth", "ninth", "tenth", "after", "back",
                  "before", "behind", "every", "front", "index", "last",
                  "middle", "some", "that", "through", "thru", "where", "whose")
    val Operators = List[String]("and", "or", "is equal", "equals", "(is )?equal to", "is not",
                 "isn't", "isn't equal( to)?", "is not equal( to)?",
                 "doesn't equal", "does not equal", "(is )?greater than",
                 "comes after", "is not less than or equal( to)?",
                 "isn't less than or equal( to)?", "(is )?less than",
                 "comes before", "is not greater than or equal( to)?",
                 "isn't greater than or equal( to)?",
                 "(is  )?greater than or equal( to)?", "is not less than",
                 "isn't less than", "does not come before",
                 "doesn't come before", "(is )?less than or equal( to)?",
                 "is not greater than", "isn't greater than",
                 "does not come after", "doesn't come after", "starts? with",
                 "begins? with", "ends? with", "contains?", "does not contain",
                 "doesn't contain", "is in", "is contained by", "is not in",
                 "is not contained by", "isn't contained by", "div", "mod",
                 "not", "(a  )?(ref( to)?|reference to)", "is", "does")
    val Control = List[String]("considering", "else", "error", "exit", "from", "if",
               "ignoring", "in", "repeat", "tell", "then", "times", "to",
               "try", "until", "using terms from", "while", "whith",
               "with timeout( of)?", "with transaction", "by", "continue",
               "end", "its?", "me", "my", "return", "of" , "as")
    val Declarations = List[String]("global", "local", "prop(erty)?", "set", "get")
    val Reserved = List[String]("but", "put", "returning", "the")
    val StudioClasses = List[String]("action cell", "alert reply", "application", "box",
                     "browser( cell)?", "bundle", "button( cell)?", "cell",
                     "clip view", "color well", "color-panel",
                     "combo box( item)?", "control",
                     "data( (cell|column|item|row|source))?", "default entry",
                     "dialog reply", "document", "drag info", "drawer",
                     "event", "font(-panel)?", "formatter",
                     "image( (cell|view))?", "matrix", "menu( item)?", "item",
                     "movie( view)?", "open-panel", "outline view", "panel",
                     "pasteboard", "plugin", "popup button",
                     "progress indicator", "responder", "save-panel",
                     "scroll view", "secure text field( cell)?", "slider",
                     "sound", "split view", "stepper", "tab view( item)?",
                     "table( (column|header cell|header view|view))",
                     "text( (field( cell)?|view))?", "toolbar( item)?",
                     "user-defaults", "view", "window")
    val StudioEvents = List[String]("accept outline drop", "accept table drop", "action",
                    "activated", "alert ended", "awake from nib", "became key",
                    "became main", "begin editing", "bounds changed",
                    "cell value", "cell value changed", "change cell value",
                    "change item value", "changed", "child of item",
                    "choose menu item", "clicked", "clicked toolbar item",
                    "closed", "column clicked", "column moved",
                    "column resized", "conclude drop", "data representation",
                    "deminiaturized", "dialog ended", "document nib name",
                    "double clicked", "drag( (entered|exited|updated))?",
                    "drop", "end editing", "exposed", "idle", "item expandable",
                    "item value", "item value changed", "items changed",
                    "keyboard down", "keyboard up", "launched",
                    "load data representation", "miniaturized", "mouse down",
                    "mouse dragged", "mouse entered", "mouse exited",
                    "mouse moved", "mouse up", "moved",
                    "number of browser rows", "number of items",
                    "number of rows", "open untitled", "opened", "panel ended",
                    "parameters updated", "plugin loaded", "prepare drop",
                    "prepare outline drag", "prepare outline drop",
                    "prepare table drag", "prepare table drop",
                    "read from file", "resigned active", "resigned key",
                    "resigned main", "resized( sub views)?",
                    "right mouse down", "right mouse dragged",
                    "right mouse up", "rows changed", "scroll wheel",
                    "selected tab view item", "selection changed",
                    "selection changing", "should begin editing",
                    "should close", "should collapse item",
                    "should end editing", "should expand item",
                    "should open( untitled)?",
                    "should quit( after last window closed)?",
                    "should select column", "should select item",
                    "should select row", "should select tab view item",
                    "should selection change", "should zoom", "shown",
                    "update menu item", "update parameters",
                    "update toolbar item", "was hidden", "was miniaturized",
                    "will become active", "will close", "will dismiss",
                    "will display browser cell", "will display cell",
                    "will display item cell", "will display outline cell",
                    "will finish launching", "will hide", "will miniaturize",
                    "will move", "will open", "will pop up", "will quit",
                    "will resign active", "will resize( sub views)?",
                    "will select tab view item", "will show", "will zoom",
                    "write to file", "zoomed")
    val StudioCommands = List[String]("animate", "append", "call method", "center",
                      "close drawer", "close panel", "display",
                      "display alert", "display dialog", "display panel", "go",
                      "hide", "highlight", "increment", "item for",
                      "load image", "load movie", "load nib", "load panel",
                      "load sound", "localized string", "lock focus", "log",
                      "open drawer", "path for", "pause", "perform action",
                      "play", "register", "resume", "scroll", "select( all)?",
                      "show", "size to fit", "start", "step back",
                      "step forward", "stop", "synchronize", "unlock focus",
                      "update")
    val StudioProperties = List[String]("accepts arrow key", "action method", "active",
                        "alignment", "allowed identifiers",
                        "allows branch selection", "allows column reordering",
                        "allows column resizing", "allows column selection",
                        "allows customization",
                        "allows editing text attributes",
                        "allows empty selection", "allows mixed state",
                        "allows multiple selection", "allows reordering",
                        "allows undo", "alpha( value)?", "alternate image",
                        "alternate increment value", "alternate title",
                        "animation delay", "associated file name",
                        "associated object", "auto completes", "auto display",
                        "auto enables items", "auto repeat",
                        "auto resizes( outline column)?",
                        "auto save expanded items", "auto save name",
                        "auto save table columns", "auto saves configuration",
                        "auto scroll", "auto sizes all columns to fit",
                        "auto sizes cells", "background color", "bezel state",
                        "bezel style", "bezeled", "border rect", "border type",
                        "bordered", "bounds( rotation)?", "box type",
                        "button returned", "button type",
                        "can choose directories", "can choose files",
                        "can draw", "can hide",
                        "cell( (background color|size|type))?", "characters",
                        "class", "click count", "clicked( data)? column",
                        "clicked data item", "clicked( data)? row",
                        "closeable", "collating", "color( (mode|panel))",
                        "command key down", "configuration",
                        "content(s| (size|view( margins)?))?", "context",
                        "continuous", "control key down", "control size",
                        "control tint", "control view",
                        "controller visible", "coordinate system",
                        "copies( on scroll)?", "corner view", "current cell",
                        "current column", "current( field)?  editor",
                        "current( menu)? item", "current row",
                        "current tab view item", "data source",
                        "default identifiers", "delta (x|y|z)",
                        "destination window", "directory", "display mode",
                        "displayed cell", "document( (edited|rect|view))?",
                        "double value", "dragged column", "dragged distance",
                        "dragged items", "draws( cell)? background",
                        "draws grid", "dynamically scrolls", "echos bullets",
                        "edge", "editable", "edited( data)? column",
                        "edited data item", "edited( data)? row", "enabled",
                        "enclosing scroll view", "ending page",
                        "error handling", "event number", "event type",
                        "excluded from windows menu", "executable path",
                        "expanded", "fax number", "field editor", "file kind",
                        "file name", "file type", "first responder",
                        "first visible column", "flipped", "floating",
                        "font( panel)?", "formatter", "frameworks path",
                        "frontmost", "gave up", "grid color", "has data items",
                        "has horizontal ruler", "has horizontal scroller",
                        "has parent data item", "has resize indicator",
                        "has shadow", "has sub menu", "has vertical ruler",
                        "has vertical scroller", "header cell", "header view",
                        "hidden", "hides when deactivated", "highlights by",
                        "horizontal line scroll", "horizontal page scroll",
                        "horizontal ruler view", "horizontally resizable",
                        "icon image", "id", "identifier",
                        "ignores multiple clicks",
                        "image( (alignment|dims when disabled|frame style|scaling))?",
                        "imports graphics", "increment value",
                        "indentation per level", "indeterminate", "index",
                        "integer value", "intercell spacing", "item height",
                        "key( (code|equivalent( modifier)?|window))?",
                        "knob thickness", "label", "last( visible)? column",
                        "leading offset", "leaf", "level", "line scroll",
                        "loaded", "localized sort", "location", "loop mode",
                        "main( (bunde|menu|window))?", "marker follows cell",
                        "matrix mode", "maximum( content)? size",
                        "maximum visible columns",
                        "menu( form representation)?", "miniaturizable",
                        "miniaturized", "minimized image", "minimized title",
                        "minimum column width", "minimum( content)? size",
                        "modal", "modified", "mouse down state",
                        "movie( (controller|file|rect))?", "muted", "name",
                        "needs display", "next state", "next text",
                        "number of tick marks", "only tick mark values",
                        "opaque", "open panel", "option key down",
                        "outline table column", "page scroll", "pages across",
                        "pages down", "palette label", "pane splitter",
                        "parent data item", "parent window", "pasteboard",
                        "path( (names|separator))?", "playing",
                        "plays every frame", "plays selection only", "position",
                        "preferred edge", "preferred type", "pressure",
                        "previous text", "prompt", "properties",
                        "prototype cell", "pulls down", "rate",
                        "released when closed", "repeated",
                        "requested print time", "required file type",
                        "resizable", "resized column", "resource path",
                        "returns records", "reuses columns", "rich text",
                        "roll over", "row height", "rulers visible",
                        "save panel", "scripts path", "scrollable",
                        "selectable( identifiers)?", "selected cell",
                        "selected( data)? columns?", "selected data items?",
                        "selected( data)? rows?", "selected item identifier",
                        "selection by rect", "send action on arrow key",
                        "sends action when done editing", "separates columns",
                        "separator item", "sequence number", "services menu",
                        "shared frameworks path", "shared support path",
                        "sheet", "shift key down", "shows alpha",
                        "shows state by", "size( mode)?",
                        "smart insert delete enabled", "sort case sensitivity",
                        "sort column", "sort order", "sort type",
                        "sorted( data rows)?", "sound", "source( mask)?",
                        "spell checking enabled", "starting page", "state",
                        "string value", "sub menu", "super menu", "super view",
                        "tab key traverses cells", "tab state", "tab type",
                        "tab view", "table view", "tag", "target( printer)?",
                        "text color", "text container insert",
                        "text container origin", "text returned",
                        "tick mark position", "time stamp",
                        "title(d| (cell|font|height|position|rect))?",
                        "tool tip", "toolbar", "trailing offset", "transparent",
                        "treat packages as directories", "truncated labels",
                        "types", "unmodified characters", "update views",
                        "use sort indicator", "user defaults",
                        "uses data source", "uses ruler",
                        "uses threaded animation",
                        "uses title from previous column", "value wraps",
                        "version",
                        "vertical( (line scroll|page scroll|ruler view))?",
                        "vertically resizable", "view",
                        "visible( document rect)?", "volume", "width", "window",
                        "windows menu", "wraps", "zoomable", "zoomed")

    val tokens = Map[String, StateDef](
        ("root", List[Definition](
            ("""\s+""", Text),
            ("""¬\n""", Str.Escape),
            ("""'s\s+""", Text), // This is a possessive, consider moving
            ("""(--|#).*?$""", Comment),
            ("""\(\*""", Comment.Multiline) >> "comment",
            ("""[\(\){}!,.:]""", Punctuation),
            ("""(«)([^»]+)(»)""",
             ByGroups(Text, Name.Builtin, Text)),
            ("""\b((?:considering|ignoring)\s*)""" +
             """(application responses|case|diacriticals|hyphens|""" +
             """numeric strings|punctuation|white space)""",
             ByGroups(Keyword, Name.Builtin)),
            ("""(-|\*|\+|&|≠|>=?|<=?|=|≥|≤|/|÷|\^)""", Operator),
            (Operators.mkString("""\b|\b"""), Operator.Word),
            ("""^(\s*(?:on|end)\s+)""" + StudioEvents.mkString("|"),
             ByGroups(Keyword, Name.Function)),
            ("""^(\s*)(in|on|script|to)(\s+)""", ByGroups(Text, Keyword, Text)),
            (Classes.mkString("""\b(as )|\b"""),
             ByGroups(Keyword, Name.Class)),
            ( Literals.mkString("""\b|\b"""), Name.Constant),
            ( Commands.mkString("""\b|\b"""), Name.Builtin),
            ( Control.mkString("""\b|\b"""), Keyword),
            ( Declarations.mkString("""\b|\b"""), Keyword),
            ( Reserved.mkString("""\b|\b"""), Name.Builtin),
            ( BuiltIn.mkString("""\b|s?\b"""), Name.Builtin),
            ( HandlerParams.mkString("""\b|\b"""), Name.Builtin),
            ( StudioProperties.mkString("""\b|\b"""), Name.Attribute),
            ( StudioClasses.mkString("""\b|s?\b"""), Name.Builtin),
            ( StudioCommands.mkString("""\b|\b"""), Name.Builtin),
            ( References.mkString("""\b|\b"""), Name.Builtin),
            (""""(\\\\|\\"|[^"])*"""", Str.Double),
            ("""\b(%s)\b""" format Identifiers, Name.Variable),
            ("""[-+]?(\d+\.\d*|\d*\.\d+)(E[-+][0-9]+)?""", Number.Float),
            ("""[-+]?\d+""", Number.Integer)
        )),
        ("comment", List[Definition](
            ("""\(\*""", Comment.Multiline) >> Push,
            ("""\*\)""", Comment.Multiline) >> Pop,
            ("""[^*(]+""", Comment.Multiline),
            ("""[*(]""", Comment.Multiline)
        ))
    )

}