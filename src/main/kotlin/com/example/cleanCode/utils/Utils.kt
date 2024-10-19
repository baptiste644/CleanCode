package com.example.cleanCode.utils

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class Utils {
    companion object {
        fun getLineIndex(content: String, regexPattern: String): Int {
            // Compile the regex pattern
            val regex = regexPattern.toRegex()

            // Find the match in the content
            val matchResult = regex.find(content)
            if (matchResult != null) {
                // Get the start index of the match in the content
                val matchStartIndex = matchResult.range.first

                // Get the substring from the start of the content to the match start
                val substringBeforeMatch = content.substring(0, matchStartIndex)

                // Count the number of newline characters in the substring to find the line index
                return substringBeforeMatch.count { it == '\n' } + 1
            }

            // If no match is found, return -1
            return -1
        }

        fun extractContentBetweenLines(filePath: String, firstLineIndex: Int, lastLineIndex: Int, e:AnActionEvent): String {
            // Lire le fichier dans une liste de lignes

            val lines = EditFileUtils.getFileContentAsList(filePath)
            Messages.showMessageDialog(e.project, "firstLineIndex" + firstLineIndex.toString(), "Test", Messages.getInformationIcon())
            Messages.showMessageDialog(e.project, "lastLineIndex" + lastLineIndex.toString(), "Test", Messages.getInformationIcon())
            Messages.showMessageDialog(e.project, (firstLineIndex < 0).toString(), "Test", Messages.getInformationIcon())
            Messages.showMessageDialog(e.project, "lines.size"+ lines.size.toString(), "Test", Messages.getInformationIcon())
            Messages.showMessageDialog(e.project, (lastLineIndex -1 >= lines.size).toString(), "Test", Messages.getInformationIcon())
            Messages.showMessageDialog(e.project, (firstLineIndex >= lastLineIndex && lastLineIndex != -1).toString(), "Test", Messages.getInformationIcon())
            // Vérifier que les indices sont valides
            if (firstLineIndex < 0 || lastLineIndex -1 >= lines.size || (firstLineIndex >= lastLineIndex && lastLineIndex != -1)) {
                throw IllegalArgumentException("Invalid line indices")
            }

            if (lastLineIndex == -1) {
                Messages.showMessageDialog(e.project, "return si lastIndex == -1", "Test", Messages.getInformationIcon())
                try {
                    Messages.showMessageDialog(e.project, "juste lines:  " + lines.toString(), "Test", Messages.getInformationIcon())
                } catch (a: Exception) {
                    Messages.showMessageDialog(e.project, "An error occurred: ${a.message}", "Test", Messages.getInformationIcon())
                    Messages.showMessageDialog(e.project, a.printStackTrace().toString(), "Test", Messages.getInformationIcon())
                }


                Messages.showMessageDialog(e.project, (lines.subList(firstLineIndex, lines.size-1).joinToString("\n")), "Test", Messages.getInformationIcon())
                return lines.subList(firstLineIndex, lines.size-1).joinToString("\n")
            }
            // Extraire les lignes entre firstLineIndex + 1 et lastLineIndex - 1
            Messages.showMessageDialog(e.project, "return ", "Test", Messages.getInformationIcon())
            Messages.showMessageDialog(e.project, (lastLineIndex -1 >= lines.size).toString(), "Test", Messages.getInformationIcon())
            return lines.subList(firstLineIndex, lastLineIndex - 1).joinToString("\n")
        }



        fun removeSpacesAndNewlinesAndTab(input: String): String {
            // Remplace tous les espaces et les retours à la ligne par une chaîne vide
            return input.replace(" ", "").replace("\n", "").replace("\t", "")
        }

        fun removeLineOfListIfLastIsEmpty(list: List<String>): List<String> {
            if (list.get(list.size - 1).isEmpty()) {
                return list.dropLast(1)
            }
            return list
        }

        fun getContentBetweenLines(path: String, firstRegexMatch: String, secondRegexMatch: String, e: AnActionEvent): Triple<String, Int, Int> {
            val content = EditFileUtils.getFileContent(path)
            val indexStart = getLineIndex(content, firstRegexMatch)
            Messages.showMessageDialog(e.project, indexStart.toString(), "Test", Messages.getInformationIcon())
            Messages.showMessageDialog(e.project, "path:" + path, "Test", Messages.getInformationIcon())
            val contentAfter = extractContentBetweenLines(path, indexStart, -1, e)
            Messages.showMessageDialog(e.project, contentAfter, "Test", Messages.getInformationIcon())
            val indexEnd = getLineIndex(contentAfter, secondRegexMatch) + indexStart

            return Triple(extractContentBetweenLines(path, indexStart, indexEnd, e), indexStart, indexEnd)
        }

        fun getTri(e: AnActionEvent): String {
            val fileName = EditFileUtils.getFileName(e)
            return fileName?.substring(0, 3).toString()
        }
    }
}