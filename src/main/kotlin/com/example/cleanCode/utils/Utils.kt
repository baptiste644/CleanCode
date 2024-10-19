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

        fun extractContentBetweenLines(filePath: String, firstLineIndex: Int, lastLineIndex: Int, e:AnActionEvent): List<String> {
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
            Messages.showMessageDialog(e.project, lines.subList(firstLineIndex, actualEndIndex).toString(), "Test", Messages.getInformationIcon())
            // Retourne les lignes entre startIndex et actualEndIndex
            return lines.subList(firstLineIndex, actualEndIndex)
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
            Messages.showMessageDialog(e.project, "variable de getContentBetweenLine" + path+ indexStart+ indexEnd.toString(), "Test", Messages.getInformationIcon())
            return Triple(extractContentBetweenLines(path, indexStart, indexEnd, e), indexStart, indexEnd)
        }

        fun getTri(e: AnActionEvent): String {
            val fileName = EditFileUtils.getFileName(e)
            return fileName?.substring(0, 3).toString()
        }
    }
}