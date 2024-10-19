package com.example.cleanCode.utils

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.ui.Messages
import com.jetbrains.rd.util.string.printToString

class Utils {
    companion object {
        fun getLineIndex(contents: List<String>, regexPattern: String): Int {
            // Compile the regex pattern
            val regex = regexPattern.toRegex()

            // Find the match in the content
            for ((index, contentLine) in contents.withIndex()) {
                if (regex.find(contentLine) != null) {
                    return index + 1
                }
            }
            return -1
        }

        fun extractContentBetweenLines(filePath: String, firstLineIndex: Int, lastLineIndex: Int, e:AnActionEvent):
                List<String> {
            // Lire le fichier dans une liste de lignes

            val lines = EditFileUtils.getFileContentAsList(filePath)

            // Vérifie si les index sont valides
            if (firstLineIndex < 0 || firstLineIndex >= lines.size) {
                throw IllegalArgumentException("L'index de départ est en dehors de la plage des lignes.")
            }

            // Si endIndex est -1, on prend jusqu'à la fin du fichier
            val actualEndIndex = if (lastLineIndex == -1) lines.size else lastLineIndex

            // Vérifie si le second index est valide
            if (actualEndIndex < firstLineIndex || actualEndIndex > lines.size) {
                throw IllegalArgumentException("L'index de fin est en dehors de la plage des lignes.")
            }

            // Retourne les lignes entre startIndex et actualEndIndex
            return lines.subList(firstLineIndex, actualEndIndex)

//            Messages.showMessageDialog(e.project, "firstLineIndex" + firstLineIndex.toString(), "Test", Messages.getInformationIcon())
//            Messages.showMessageDialog(e.project, "lastLineIndex" + lastLineIndex.toString(), "Test", Messages.getInformationIcon())
//            Messages.showMessageDialog(e.project, (firstLineIndex < 0).toString(), "Test", Messages.getInformationIcon())
//            Messages.showMessageDialog(e.project, "lines.size"+ lines.size.toString(), "Test", Messages.getInformationIcon())
//            Messages.showMessageDialog(e.project, (lastLineIndex -1 >= lines.size).toString(), "Test", Messages.getInformationIcon())
//            Messages.showMessageDialog(e.project, (firstLineIndex >= lastLineIndex && lastLineIndex != -1).toString(), "Test", Messages.getInformationIcon())
//            // Vérifier que les indices sont valides
//            if (firstLineIndex < 0 || lastLineIndex - 1 >= lines.size || (firstLineIndex >= lastLineIndex && lastLineIndex != -1)) {
//                throw IllegalArgumentException("Invalid line indices")
//            }

//            if (lastLineIndex == -1) {
                // Messages.showMessageDialog(                e.project,                    "return si lastIndex == -1",                    "Test",  Messages.getInformationIcon()                )
//                val logger = Logger.getInstance(Utils::class.java)
//                try {
//                    logger.info("varible line")
//                    logger.info(EditFileUtils.getFileContent(filePath))
//                    Messages.showMessageDialog(e.project, "juste lines:  " + lines.toString(), "Test", Messages.getInformationIcon())
//                } catch (a: Exception) {
//                    logger.error("erreur", a)
//                    Messages.showMessageDialog(e.project, "An error occurred: ${a.message}", "Test", Messages.getInformationIcon())
//                    Messages.showMessageDialog(e.project, a.printStackTrace().toString(), "Test", Messages.getInformationIcon())
//                }
//
//                var index = 0;
//                var ligneVideindex = 0;
//                var response = ""
//                for (line in lines) {
//                    if (index >= firstLineIndex) {
//                        response = response + line
//                    }
//                    if (index == firstLineIndex || index == firstLineIndex + 1) {
//                        Messages.showMessageDialog(e.project, (line::class).toString(), "Test", Messages.getInformationIcon())
//                        Messages.showMessageDialog(e.project, line, "Test", Messages.getInformationIcon())
//                        var ligne = line
//                        Messages.showMessageDialog(e.project, response, "Test", Messages.getInformationIcon())
//                        Messages.showMessageDialog(e.project, ligne, "Test", Messages.getInformationIcon())
//                        Messages.showMessageDialog(e.project, response.plus(ligne), "Test", Messages.getInformationIcon())
//
//                    }
//                    index++
//                    if (line == "") {
//                        ligneVideindex++
//                    }
//                }
//                Messages.showMessageDialog(e.project, "1er index" + index + "2eme index" + ligneVideindex, "Test", Messages.getInformationIcon())
//                Messages.showMessageDialog(e.project, "return " + response, "Test", Messages.getInformationIcon())
//                Messages.showMessageDialog(e.project, (lines::class).toString(), "Test", Messages.getInformationIcon())
//                Messages.showMessageDialog(e.project, lines.size.toString(), "Test", Messages.getInformationIcon())
//                Messages.showMessageDialog(e.project, "la response est ", "Test", Messages.getInformationIcon())
//                Messages.showMessageDialog(e.project, "la response est "+ response.toString(), "Test", Messages.getInformationIcon())
//                return response
//                //return lines.subList(firstLineIndex, lines.size-1).joinToString("\n")
//            }
//            // Extraire les lignes entre firstLineIndex + 1 et lastLineIndex - 1
//            Messages.showMessageDialog(e.project, "return ", "Test", Messages.getInformationIcon())
//            Messages.showMessageDialog(
//                e.project,
//                (lastLineIndex - 1 >= lines.size).toString(),
//                "Test",
//                Messages.getInformationIcon()
//            )
//            return lines.subList(firstLineIndex, lastLineIndex - 1).joinToString("\n")
        }


        fun removeSpacesAndNewlinesAndTab(inputs: List<String>): List<String> {
            var response = mutableListOf<String>()
            for (input in inputs) {
                response.add(input.replace(" ", "").replace("\n", "").replace("\t", "").replace(",", """"""))
            }
            return response
        }

        fun removeLineOfListIfLastIsEmpty(list: List<String>): List<String> {
            if (list.get(list.size - 1).isEmpty()) {
                return list.dropLast(1)
            }
            return list
        }

        fun getContentBetweenLines(path: String, firstRegexMatch: String, secondRegexMatch: String, e: AnActionEvent): Triple<List<String>, Int, Int> {
            val content = EditFileUtils.getFileContentAsList(path)
            val indexStart = getLineIndex(content, firstRegexMatch)
            val contentAfter = extractContentBetweenLines(path, indexStart, -1, e)
            val indexEnd = getLineIndex(contentAfter, secondRegexMatch) + indexStart - 1

            return Triple(extractContentBetweenLines(path, indexStart, indexEnd, e), indexStart, indexEnd)
        }

        fun getTri(e: AnActionEvent): String {
            val fileName = EditFileUtils.getFileName(e)
            return fileName?.substring(0, 3).toString()
        }
    }
}