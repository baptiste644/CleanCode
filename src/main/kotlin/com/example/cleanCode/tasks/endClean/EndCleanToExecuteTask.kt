package com.example.cleanCode.tasks.endClean

import com.intellij.openapi.actionSystem.AnActionEvent

class EndCleanToExecuteTask {
    fun EndCleanToExecuteTask(e: AnActionEvent, list: List<String>) {
        if (list.contains("PropsType")) {
            PropsTypeCleanTask().PropsTypeCleanTask(e)
        }
    }
}