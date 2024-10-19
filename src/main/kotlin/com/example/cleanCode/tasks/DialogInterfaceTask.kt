package com.example.cleanCode.tasks

import ai.grazie.detector.ngram.main
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.RadioButton
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.Component
import java.awt.GridLayout
import javax.swing.*
import javax.swing.border.EmptyBorder

class DialogInterfaceTask : DialogWrapper(true) {
    private lateinit var radioButtons1: JRadioButton
    private lateinit var radioButtons2: JRadioButton
    private lateinit var checkBoxPanel: JPanel
    private lateinit var mainPanel: JPanel

    private val checkBoxList = mutableListOf<JCheckBox>()

    init {
        init()
        title = "Sélectionnez une tâche"
        showCheckBoxes()
    }

    override fun createCenterPanel(): JComponent {
        mainPanel = JPanel()
        mainPanel.layout = BoxLayout(mainPanel, BoxLayout.Y_AXIS)

        radioButtons1 = JRadioButton("end Cleaning")
        radioButtons2 = JRadioButton("test task")

        val group = ButtonGroup()
        group.add(radioButtons1)
        group.add(radioButtons2)

        radioButtons1.isSelected = true

        checkBoxPanel = JPanel()
        checkBoxPanel.layout = BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS)
        checkBoxPanel.isVisible = true

        mainPanel.add(radioButtons1)
        mainPanel.add(checkBoxPanel)
        mainPanel.add(radioButtons2)

        radioButtons1.addItemListener {
            showCheckBoxes()
        }

        radioButtons2.addItemListener {
            checkBoxPanel.isVisible = false
        }
        return mainPanel
    }

    private fun showCheckBoxes() {
        checkBoxPanel.removeAll()
        checkBoxList.clear()

        val checkBox1 = JCheckBox("PropsType")
        val checkBox2 = JCheckBox("DefaultProps")
        val checkBox3 = JCheckBox("export Page")

        checkBoxList.add(checkBox1)
        checkBoxList.add(checkBox2)
        checkBoxList.add(checkBox3)

        val indent = 20
        checkBoxList.forEach { checkBox ->
            checkBox.alignmentX = Component.LEFT_ALIGNMENT
            checkBox.border = JBUI.Borders.emptyLeft(indent)
            checkBoxPanel.add(checkBox)
        }

        checkBoxPanel.isVisible = true

        mainPanel.revalidate()
        mainPanel.repaint()
    }

    fun getSelectedTask(): String? {
        if (radioButtons1.isSelected) {
            return radioButtons1.text.toString()
        } else if (radioButtons2.isSelected) {
            return radioButtons2.text.toString()
        }
        return null
    }

    fun getSelectingCleaningTask(): List<String> {
        return checkBoxList.filter { it.isSelected }.map { it.text.toString() }
    }
}