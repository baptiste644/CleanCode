package com.example.cleanCode.actions

import com.example.cleanCode.tasks.DialogInterfaceTask
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.DialogWrapper
import com.example.cleanCode.tasks.TestTask
import com.example.cleanCode.tasks.endClean.EndCleanToExecuteTask

class ShowPopupAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val dialog = DialogInterfaceTask()
        dialog.show()

        if (dialog.exitCode == DialogWrapper.OK_EXIT_CODE) {
            val selected = dialog.getSelectedTask()
            println("Tâche sélectionnée : $selected")
            if (selected == "end Cleaning") {
                val checkBoxSelected = dialog.getSelectingCleaningTask()
                EndCleanToExecuteTask().EndCleanToExecuteTask(e, checkBoxSelected)
            }
            if (selected === "test task") {
                TestTask().testTask(e)
            }
        }
    }
}