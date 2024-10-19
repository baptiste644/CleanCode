package com.example.cleanCode.utils

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import java.io.File


class EditFileUtils {
    companion object {
        fun getFileContent(path: String): String {
            var documentString = ""
            val virtualFile: VirtualFile? = VfsUtil.findFileByIoFile(File(path), true)

            if (virtualFile != null) {
                // Récupère le document associé au fichier
                val document: Document? = FileDocumentManager.getInstance().getDocument(virtualFile)

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

        fun getFileContentAsList(path: String): List<String> {
            return getFileContent(path).split("\n")
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