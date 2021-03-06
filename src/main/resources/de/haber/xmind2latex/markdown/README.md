# XMind to Latex
XMind to Latex is a tool that reads a XMind mind map and derives a structured latex document. 
This way, chapters of a book may be structured as a mind map that is then exported to latex.   

## Release
The latest XMind to Latex release can be found [here](https://github.com/arnehaber/xmind2latex/releases/latest) 

## Folder and File Structure
* _bin_ - start scripts
    * _xmind2latex.bat_ - windows start script
    * _xmind2latex.sh_  - unix/linux start script
* _doc_
    * _commands.txt_ - printed help message included in the documentation
    * _md*.*_ - style css files used in the documentation
* _license_ - contains licenses and further dependencies information
* _repo_ - contains dependencies, add custom templates here
    * _*.jar_ project dependencies
* _README.html_ - this readme file


## Command Line Parameters

<iframe src="doc/commands.txt" scrolling="no" height="480">
Please note that the following list may be outdated. A complete list of the current command line parameters is contained
in the <b>README.html</b> file of the <a href="https://github.com/arnehaber/xmind2latex/releases/latest" target="_blank">release</a>.
<pre><code>
    usage: xmind2latex
     -e,--env <level> <start> <end>           Sets the start and end environment templates for the given
                                              level (optional). Templates must be either loadable from
                                              the classpath with the given full qualified name (no file
                                              extension, directories separated by a '.', or as a file
                                              (with '.ftl' extension, directories separated by a path
                                              separator).
     -f,--force                               Force overwrite existing files (optional).
     -h,--help                                Prints this help message.
     -i,--input <input file>                  Required input file name.
     -l,--level-template <level> <template>   Sets the template that is to be used for the given level
                                              (optional). Templates must be either loadable from the
                                              classpath with the given full qualified name (no file
                                              extension, directories separated by a '.', or as a file
                                              (with '.ftl' extension, directories separated by a path
                                              separator).
     -o,--output <output file>                Output file name (optional). Default output file is
                                              "<input file>.tex."
     -t,--template-level <template level>     Maximal level for template usage.                                              
</code></pre>
</iframe>


## Default Configuration
* no environments added for any hierarchical level
* templates for levels (0 = template that is used, if no other template is given for a level): 
    * _0._ de.haber.xmind2latex.templates.undefined
    * _1._ de.haber.xmind2latex.templates.chapter
    * _2._ de.haber.xmind2latex.templates.section
    * _3._ de.haber.xmind2latex.templates.subsection
    * _4._ de.haber.xmind2latex.templates.subsubsection
* do not overwrite existing files
* output file = input file + ".tex"

## Shipped templates in package _de.haber.xmind2latex.templates_:
* _undefined_     - creates a latex comment prefixed with the innerLevel
* _chapter_       - creates a latex chapter
* _section_       - creates a latex section
* _subsection_    - creates a latex subsection
* _subsubsection_ - creates a latex subsubsection
* _paragraph_     - creates a latex paragraph
* _subparagraph_  - creates a latex subparagraph

## Shipped templates in package _de.haber.xmind2latex.templates.env_:
* _startEnumerate_ - start template for an enumerate environment
* _endEnumerate_   - end template for an enumerate environment
* _startItemize_   - start template for an itemize environment
* _endItemize_     - end template for an itemize environment
* _item_           - template for an item

## User Defined Templates
Parameters _-l_ and _-e_ may be used to customize the produced latex document with user defined templates.
The further allows to use a custom template for a certain hierarchical level. The latter allows to open and close
an environment using a start- respectively end-template when a certain level is entered respectively left.
If user defined templates should be processed, these have to be either located in the _repo_ folder or sub directories 
to load it from classpath (no file extension, use a '.' as path separator), or the regular file name of the 
template has to be passed to the tool (with ".ftl" extension and regular path separators).  
In templates, the following variables are available:

* _text_          - input text from processed XMind file 
* _level_         - hierarchical level of the currently processed text
* _innerLevel_    - hierarchical level of used default templates
* _indent_        - indentions (spaces) corresponding to the current inner level

## Version History
### 1.3.0

### 1.2.0
* Adds version parameter (issue [#11](https://github.com/arnehaber/xmind2latex/issues/11)).
* UTF-8 output encoding (issue [#10](https://github.com/arnehaber/xmind2latex/issues/10)).
* Adds a builder to configure the exporter (issue [#9](https://github.com/arnehaber/xmind2latex/issues/9)).
* Escape line breaks in printed latex comments (issue [#8](https://github.com/arnehaber/xmind2latex/issues/8)).
* Adds check for missing parameter arguments (issue [#7](https://github.com/arnehaber/xmind2latex/issues/7)).

### 1.1.0
* Read xml as well as XMind source files using an input stream to omit extraction of XMind files (issue [#2](https://github.com/arnehaber/xmind2latex/issues/2)). 
* Removed '.sh' from linux execution script (issue [#3](https://github.com/arnehaber/xmind2latex/issues/3)).
* Added parameter 't' to control until which level templates are used (issue [#4](https://github.com/arnehaber/xmind2latex/issues/4)).
* Undefined entries are now indented according to their level (issue [#5](https://github.com/arnehaber/xmind2latex/issues/5)).
* Set +x flag of the generated Linux execution script (issue [#6](https://github.com/arnehaber/xmind2latex/issues/6)).

### 1.0.0 
* initial release

## License

    Copyright 2014 Arne Haber
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
    http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

A copy of the complete license can be found [here](./license/LICENSE "License").

All test input files are downloaded from [http://www.xmind.net/share/](http://www.xmind.net/share/).

## Dependencies

<iframe src="license/THIRD-PARTY.txt" scrolling="no" height="150">

A list of dependencies is contained in the <b>README.html</b> file of the 
<a href="https://github.com/arnehaber/xmind2latex/releases/latest" target="_blank">release</a>.

</iframe>

This product includes software (Freemarker) developed by the Visigoth Software Society [http://www.visigoths.org/](http://www.visigoths.org/).
