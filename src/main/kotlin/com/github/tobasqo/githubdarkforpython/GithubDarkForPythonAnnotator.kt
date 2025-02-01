package com.github.tobasqo.githubdarkforpython

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.jetbrains.python.psi.*
import org.jetbrains.annotations.NotNull

class GithubDarkForPythonAnnotator : Annotator {
    override fun annotate(@NotNull element: PsiElement, @NotNull holder: AnnotationHolder) {
        val isLeaf = element is LeafPsiElement
        val isNotInstanceItself = element.text != element.parent.text
        val isNotCall = element.parent.parent !is PyCallExpression
        val isInstanceField = element.parent is PyReferenceExpression

        if (isLeaf && isNotCall && isNotInstanceItself && isInstanceField) {
            val range = TextRange(element.textRange.startOffset, element.textRange.endOffset)
            holder
                .newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(range)
                .textAttributes(attributes)
                .create()
        }
    }
}

val attributes = TextAttributesKey.createTextAttributesKey("PY.IDENTIFIER")