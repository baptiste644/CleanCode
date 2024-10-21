package com.example.cleanCode.tasks

import com.example.cleanCode.utils.EditFileUtils
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

class TestTask {
    fun testTask(e: AnActionEvent) {
        val project: Project? = e.project

        Messages.showMessageDialog(project, EditFileUtils.getFileName(e).toString(), "Test", Messages.getInformationIcon())
        Messages.showMessageDialog(project, EditFileUtils.getFileContent(EditFileUtils.getCurrentFilePath(e)).toString(), "Test", Messages.getInformationIcon())
    }
}