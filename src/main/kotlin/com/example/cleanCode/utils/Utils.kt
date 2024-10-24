package com.example.cleanCode.utils

import com.intellij.openapi.actionSystem.AnActionEvent

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

        fun extractContentBetweenLines(filePath: String, firstLineIndex: Int, lastLineIndex: Int): String {
            // Lire le fichier dans une liste de lignes
            val lines = EditFileUtils.getFileContentAsList(filePath)

            // Vérifier que les indices sont valides
            if (firstLineIndex < 0 || lastLineIndex -1 >= lines.size || (firstLineIndex >= lastLineIndex && lastLineIndex != -1)) {
                throw IllegalArgumentException("Invalid line indices")
            }

            if (lastLineIndex == -1) {
                return lines.subList(firstLineIndex, lines.size).joinToString("\n")
            }
            // Extraire les lignes entre firstLineIndex + 1 et lastLineIndex - 1
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

        fun getContentBetweenLines(path: String, firstRegexMatch: String, secondRegexMatch: String): Triple<String, Int, Int> {
            val content = EditFileUtils.getFileContent(path)
            val indexStart = getLineIndex(content, firstRegexMatch)
            val contentAfter = extractContentBetweenLines(path, indexStart, -1)
            val indexEnd = getLineIndex(contentAfter, secondRegexMatch) + indexStart

            return Triple(extractContentBetweenLines(path, indexStart, indexEnd), indexStart, indexEnd)
        }

        fun getTri(e: AnActionEvent): String {
            val fileName = EditFileUtils.getFileName(e)
            return fileName?.substring(0, 3).toString()
        }
    }
}