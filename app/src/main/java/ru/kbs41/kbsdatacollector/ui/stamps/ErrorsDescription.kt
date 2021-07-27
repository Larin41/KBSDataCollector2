package ru.kbs41.kbsdatacollector.ui.stamps

data class ErrorsDescription(
    val hasProblems: Boolean = false,
    val stampsAreCollected: Boolean = false,
    val problemsWithBarcodeFormat: Boolean = false,
    val problemsWithExistedStamp: Boolean = false,
    val scanningComplete: Boolean = false
) {

}