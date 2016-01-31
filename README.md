# MusicCompiler
  This is a toy compiler written in Java.  Its aim is to be a fairly general
language which has a somewhat simple syntax.  It takes its influence from
FORTRAN, Java, MATLAB, C++, and Python.  It aims to be completely cross-platform
with Windows and Linux.

## Language Features
  - Variables are passed and returned by reference
  - For loops are all for\_each loops in the style of python
  - No main() function, PROGRAM governs the order of execution
  - COLLECTION replaces class, struct, enum, and namespace
  - AUTO for type inferencing

## Progress
- [x] Lexer
- [ ] Parser
- [ ] AST Generation
- [ ] Java Bytecode output

## Hello World
```
#remix io;

PROGRAM Hello World
  io::println("Hello World");
END PROGRAM Hello World
```

## Other Languages
If you are interested in the specific programming of my language,
I encourage you to look at these other projects:
- [Kraken](https://github.com/Limvot/kraken)
- [Neuro](https://github.com/lotusronin/neuro)
