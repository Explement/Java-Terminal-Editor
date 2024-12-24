<div align="center">
  <img src="icon.png" width = 756px height = 124px/>
</div>

# Java Terminal Editor

This project is a basic text editor (similar to Notepad) that provides syntax coloring for Java code. It is designed to run directly in the Terminal and has been confirmed to work in:

- **PowerShell**
- **Windows CMD**
- **VS Code Terminal**

## Features
- **Syntax Coloring:** Basic support for Java syntax highlighting.
- **Cross-Terminal Support:** Compatible with multiple terminal environments on Windows.
- **Interactive File Editing:** Edit existing files or create new ones via a simple popup interface.
- **Massive Keyboard Support:** This has support for the following letters alternative letters; {1234567890 -= [] \ ; ',. / HOME END}

While not a replacement for tools like Vim or more advanced terminal IDEs, this isn't meant to be used, it was made as a fun challenge.

---

## Requirements
Before running this project, ensure the following tools are installed:

- **Java Development Kit (JDK)**: Version 8 or higher.
- **Maven**: To handle dependencies and run the application.

---

## Installation & Usage
Follow these steps to set up and run the project from any location:

### 1. Clone the Repository
Download or clone the repository to your local machine.

```bash
# Clone the repository
git clone https://github.com/Explement/Java-Terminal-Editor
cd <repository-folder>
```

### 2. Compile the Project
Compile the project using Maven. This step is optional but recommended to ensure all dependencies are resolved.

```bash
mvn compile
```

### 3. Run the Program
Use the following Maven command to launch the program:

```bash
mvn exec:java -Dexec.mainClass=basic.editor.Main
```

When prompted, provide the file path to an existing file, or enter a new file path to create one. The application will display the raw content of the file, allowing you to start editing interactively.

---

## Notes

### Limitations
- This project is not intended to replace advanced text editors or IDEs.
- Syntax coloring is limited to basic Java code.

### Known Issues
- File handling may vary depending on the provided path format.
- Syntax coloring may not support edge cases or complex Java constructs.
- Syntax coloring focus color will be white, not really a 'glitch', but can be an inconvenience.

### Future Improvements
- Add support for more programming languages. {Rust, JavaScript, Lua, C#, C++}
- Add focus colors when the cursor is inside of a keyword.
- Add themes codable in Python or Lua.

---

## Contributing
Contributions are welcome! Feel free to fork the repository, create a new branch, and submit a pull request with your changes.

---

## License
This project has **NO** license, as its just a small project.

---

## Acknowledgements
This project was just for fun, but I did get help from [@JadeYeti](https://github.com/Jadeeeeeeeeeeee)



