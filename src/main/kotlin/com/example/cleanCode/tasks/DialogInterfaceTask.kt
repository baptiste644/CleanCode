package com.example.cleanCode.tasks

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.RadioButton
import java.awt.BorderLayout
import java.awt.GridLayout
import javax.swing.ButtonGroup
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JRadioButton

class DialogInterfaceTask : DialogWrapper(true) {
    private val radioButtons = listOf(
        JRadioButton("Tâche 1"),
        JRadioButton("Tâche 2"),
        JRadioButton("Tâche 3")
    )

    init {
        init()
        title = "Sélectionnez une tâche"

        // Sélectionne le premier bouton radio par défaut
        if (radioButtons.isNotEmpty()) {
            radioButtons[0].isSelected = true
        }
    }

    override fun createCenterPanel(): JComponent {
        val buttonGroup = ButtonGroup()
        val panel = JPanel(GridLayout(radioButtons.size, 1))

        for (radioButton in radioButtons) {
            buttonGroup.add(radioButton)
            panel.add(radioButton)
        }

        return panel
    }

    fun getSelectedTask(): String? {
        return radioButtons.find { it.isSelected }?.text
    }
}