package com.example.cleanCode.tasks

import com.example.cleanCode.utils.EditFileUtils
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

class TestTask {
    fun testTask(e: AnActionEvent) {
        val project: Project? = e.project

        val content = EditFileUtils.getPageFromView(e)

        Messages.showMessageDialog(project, content, "Test", Messages.getInformationIcon())
    }
}