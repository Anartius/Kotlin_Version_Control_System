package svcs

import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path

fun main(args: Array<String>) {
    try {
        Files.createDirectory(Path("vcs/"))
    } catch (e: Exception) {}


    val helpMessage = """
        These are SVCS commands:
        config     Get and set a username.
        add        Add a file to the index.
        log        Show commit logs.
        commit     Save changes.
        checkout   Restore a file.
    """.trimIndent()

    if (args.isNotEmpty()) {
        when (args[0]) {
            "config" -> config(args)
            "add" -> add(args)
            "log" -> println("Show commit logs.")
            "commit" -> println("Save changes.")
            "checkout" -> println("Restore a file.")
            "--help" -> println(helpMessage)
            else -> println("\'${args[0]}\' is not a SVCS command.")
        }
    } else println(helpMessage)
}


fun add(args: Array<String>) {
    val fileName = "vcs/index.txt"
    val file = File(fileName)

    val fileNames = mutableSetOf<String>()

    val addedFile = if (args.size == 2) args[1] else ""

    if (file.exists()) {
        fileNames.addAll(file.readLines())
        if (args.size == 1) {
            println("Tracked files:\n${fileNames.joinToString("\n")}")
        } else {
            if (File(addedFile).exists()) {
                fileNames.add(addedFile)
                file.writeText(fileNames.joinToString("\n"))
                println("The file \'$addedFile\' is tracked.")
            } else println("Can't find \'$addedFile\'.")
        }

    } else {
        if (args.size == 1) {
            println("Add a file to the index.")
        } else {
            if (File(addedFile).exists()) {
                file.writeText(addedFile)
                println("The file \'$addedFile\' is tracked.")
            } else println("Can't find \'$addedFile\'.")
        }
    }
}


fun config(args: Array<String>) {
    val fileName = "vcs/config.txt"
    val file = File(fileName)
    var userName = if (args.size > 1) args[1] else ""

    if (file.exists()) {
        if (args.size == 1) {
            userName = file.readText()
            println("The username is $userName.")
        } else {
            file.writeText(userName)
            println("The username is $userName.")
        }

    } else {
        if (args.size == 1) {
            println("Please, tell me who you are.")
        } else {
            file.writeText(userName)
            println("The username is $userName.")
        }
    }
}