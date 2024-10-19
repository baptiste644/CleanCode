package com.example.cleanCode.constants

class Dico {

    object regex {
        const val matchAnyString = "(.|\\n)*"
        const val matchLocalizedFragmentPart1 = "LocalizedFragment\\("
        const val matchLocalizedFragmentPart2 = "Dico\\)\\(\\(\\{"
        const val matchArrow = "}\\) => "
        const val matchPropsType = ".propTypes = \\{"
        const val matchDefaultProps = ".defaultProps = \\{"
        const val matchBracket = "\\}"
        const val matchBracketAndComaPoint = "\\}\\;"
        const val matchWithNoLetterAndNumber = "[^0-9^a-z^A-Z]"
        const val matchTab = "\t"
        const val matchStartExportPropsInPage = ""
    }

    object information {
        const val information = "Information"
        const val erreur = "Erreur"
        const val more1Match = "You have more than 1 match"
        const val executeSuccess = "Execute success"
    }

    object type {
        const val string = "string"
        const val function = "function"
        const val obj = "obj"
        const val propsTypeString = ": PropsTypes.string,"
        const val propsTypeFunction = ": PropsTypes.func,"
        const val propsTypeObject = ": PropsTypes.shape({}),"

    }

    object valeur {
        const val messageBeforeDeclarationPropsType = "// prop types validation"
    }
}