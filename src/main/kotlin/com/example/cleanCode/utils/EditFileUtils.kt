package com.example.cleanCode.utils

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import java.io.File
import java.io.IOException


class EditFileUtils {
    companion object {
        fun getFileContent(path: String, event: AnActionEvent): String {
            var documentString = ""
            try {
                val virtualFil: VirtualFile? = VfsUtil.findFileByIoFile(File(path), true)
                Messages.showMessageDialog(event.project, virtualFil.toString(), "Test", Messages.getInformationIcon())
            } catch (e: Exception) {
                Messages.showMessageDialog(event.project, e.toString(), "Test", Messages.getInformationIcon())

            }
            val virtualFile: VirtualFile? = VfsUtil.findFileByIoFile(File(path), true)

            if (virtualFile != null) {
                // Récupère le document associé au fichier
                val document: Document? = FileDocumentManager.getInstance().getDocument(virtualFile)
                try {
                    if (document != null) {
                        // Obtient le contenu du fichier
                        documentString = document.text
                        Messages.showMessageDialog(event.project, documentString, "Test", Messages.getInformationIcon())
                    }
                } catch (e: IOException) {
                    Messages.showMessageDialog(event.project, e.message, "Test", Messages.getInformationIcon())
                }
                Messages.showMessageDialog(event.project, document.toString(), "Test", Messages.getInformationIcon())
                if (document != null) {
                    // Obtient le contenu du fichier
                    documentString = document.text

                } else {
                    throw IllegalArgumentException("Could not get the document for the file: $path")
                }

            } else {
                throw IllegalArgumentException("File not found: $path")
            }
            return documentString
        }

        fun getFileContentAsList(path: String, event: AnActionEvent): List<String> {
            return getFileContent(path, event).split("\n")
        }

        fun addLineAtIndex(event: AnActionEvent, path: String, content: String, lineIndex: Int) {
            val project: Project? = event.project
            // Récupère le fichier virtuel associé au chemin donné
            val virtualFile: VirtualFile? = VfsUtil.findFileByIoFile(File(path), true)

            if (virtualFile != null) {
                // Récupère le document associé au fichier
                val document: Document? = FileDocumentManager.getInstance().getDocument(virtualFile)

                if (document != null) {
                    // Exécute une action de modification qui peut être annulée
                    WriteCommandAction.runWriteCommandAction(project) {
                        val lineStartOffset = document.getLineStartOffset(lineIndex)
                        val lineEndOffset = document.getLineEndOffset(lineIndex)

                        // Ajoute le contenu dupliqué à la fin du fichier
                        document.insertString(lineStartOffset, content + "\n")
                    }
                } else {
                    throw IllegalArgumentException("Could not get the document for the file: $path")
                }
            } else {
                throw IllegalArgumentException("File not found: $path")
            }
        }

        fun removeLineAtIndex(event: AnActionEvent, path: String, lineIndex: Int) {
            val project: Project? = event.project
            // Récupère le fichier virtuel associé au chemin donné
            val virtualFile: VirtualFile? = VfsUtil.findFileByIoFile(File(path), true)

            if (virtualFile != null) {
                // Récupère le document associé au fichier
                val document: Document? = FileDocumentManager.getInstance().getDocument(virtualFile)

                if (document != null) {
                    // Exécute une action de modification qui peut être annulée
                    WriteCommandAction.runWriteCommandAction(project) {
                        val lineStartOffset = document.getLineStartOffset(lineIndex)
                        val lineEndOffset = document.getLineEndOffset(lineIndex)

                        // Supprime la ligne spécifiée par l'index
                        document.deleteString(lineStartOffset, lineEndOffset + 1)
                    }
                } else {
                    throw IllegalArgumentException("Could not get the document for the file: $path")
                }
            } else {
                throw IllegalArgumentException("File not found: $path")
            }
        }

        fun getCurrentFilePath(event: AnActionEvent): String {
            val project = event.project ?: return ""
            val file: VirtualFile? = FileEditorManager.getInstance(project).selectedFiles.firstOrNull()
            return file?.path ?: "No file is currently open"
        }

        fun getFileName(event: AnActionEvent): String? {
            val project = event.project ?: return null
            val file: VirtualFile? = FileEditorManager.getInstance(project).selectedFiles.firstOrNull()
            return file?.name
        }

        fun getPageFromView(e: AnActionEvent): String {
            val fileName = getFileName(e)
            val tri = fileName?.substring(0, 3)
            val newPath = getCurrentFilePath(e)
            val list = newPath.split("/" + tri + "/").toMutableList()
            list[1] = list[1].replace("components", "pages").replace("View.jsx", "Page.jsx")
            println(list.joinToString("/" + tri + "/"))
            return list.joinToString("/" + tri + "/")
        }
    }
}