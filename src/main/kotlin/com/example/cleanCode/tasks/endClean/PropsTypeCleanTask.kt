package com.example.cleanCode.tasks.endClean

import com.example.cleanCode.constants.Dico
import com.example.cleanCode.utils.EditFileUtils
import com.example.cleanCode.utils.Utils
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

class PropsTypeCleanTask {
    fun PropsTypeCleanTask(e: AnActionEvent) {
        val path = EditFileUtils.getCurrentFilePath(e)
        val matchLocalizedFragment = Dico.regex.matchLocalizedFragmentPart1 + Utils.getTri(e) + Dico.regex.matchLocalizedFragmentPart2
        Messages.showMessageDialog(e.project, matchLocalizedFragment, "Test", Messages.getInformationIcon())
        val (contentBetweenLines, _, _) = Utils.getContentBetweenLines(path, matchLocalizedFragment, Dico.regex.matchArrow, e)

        val listOfProps = Utils.removeLineOfListIfLastIsEmpty(Utils.removeSpacesAndNewlinesAndTab(contentBetweenLines).split(","))
        Messages.showMessageDialog(e.project, listOfProps.toString(), "Test", Messages.getInformationIcon())
        cleanByDeletingForDefaultOrType(e, path, Dico.regex.matchPropsType, Dico.regex.matchBracket, listOfProps, e)
    }

    private fun cleanByDeletingForDefaultOrType(event: AnActionEvent, path: String, firstRegexMatch: String, secondRegexMatch: String, listOfProps: List<String>, e: AnActionEvent) {
        val (contentBetweenLines, indexStart, indexEnd) = Utils.getContentBetweenLines(path, firstRegexMatch, secondRegexMatch, e)

        val listOfPropsToCheck = Utils.removeLineOfListIfLastIsEmpty(Utils.removeSpacesAndNewlinesAndTab(contentBetweenLines).split(","))
        val listOfPropsToCheckClean = listOfPropsToCheck.map { it.substringBefore(':') }
        removeIfPropsDoesNotExist(event, path, listOfProps, listOfPropsToCheckClean, indexStart, indexEnd)
    }

    private fun removeIfPropsDoesNotExist(event: AnActionEvent,path: String, listOfProps: List<String>, listOfPropsType: List<String>,indexStartProps : Int, indexEndProps: Int) {
        val resultList = listOfPropsType
            .filter { it !in listOfProps }

        if (resultList.size != 0) {
            var liste = mutableListOf<Int>()
            var lines = EditFileUtils.getFileContentAsList(path)
            for (i in indexStartProps..indexEndProps - 1) {
                for (indexResult in 0..resultList.size - 1) {
                    // Filtrer les lignes qui ne contiennent pas la chaîne "val"
                    val regex = Regex(resultList.get(indexResult) + Dico.regex.matchWithNoLetterAndNumber)
                    val content = regex.containsMatchIn(lines[i])
                    if (content) {
                        // Supprimer la ligne à l'index spécifié
                        liste.add(i)
                        resultList.drop(indexResult)
                        break
                    }
                }
            }
            liste.sort()
            liste.reversed().forEach { index -> EditFileUtils.removeLineAtIndex(event, path,index)}
            Messages.showMessageDialog(event.project,
                "Nettoyage fini",
                "Done",
                Messages.getInformationIcon())
        } else {
            Messages.showMessageDialog(event.project,
                "Props déjà à jour",
                "Information",
                Messages.getInformationIcon())
        }
    }
}