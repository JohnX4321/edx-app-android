package org.edx.mobile.model.course

import com.google.gson.annotations.SerializedName
import org.edx.mobile.util.CourseDateType
import org.edx.mobile.util.DateUtil

data class CourseDateBlock(
        @SerializedName("complete") var complete: Boolean = false,
        @SerializedName("date") val date: String = "",
        @SerializedName("date_type") var date_type: String? = "",
        @SerializedName("description") val description: String = "",
        @SerializedName("learner_has_access") var learner_has_access: Boolean = false,
        @SerializedName("link") val link: String = "",
        @SerializedName("link_text") val link_text: String = "",
        @SerializedName("title") val title: String = "",
        var dateBlockTag: CourseDateType = CourseDateType.BLANK

) {
    fun isToday(): Boolean = (DateUtil.isDateToday(date) || date_type.equals(DateTypes.TODAY_DATE))

    fun getFormattedDate(): String = DateUtil.formatCourseDate(date)

    fun getSimpleDateTime(): String = DateUtil.convertToSimpleDate(date)

    fun isDatePassed(): Boolean = DateUtil.isDatePast(date)

    fun isAssignment(): Boolean = date_type.equals(DateTypes.ASSIGNMENT_DUE_DATE)

    fun isLearnerAssignment(): Boolean = learner_has_access && isAssignment()

    fun showLink(): Boolean = link.isNotBlank() && isLearnerAssignment()

    object DateTypes {
        const val TODAY_DATE = "todays-date"
        const val COURSE_START_DATE = "course-start-date"
        const val COURSE_END_DATE = "course-end-date"
        const val COURSE_EXPIRED_DATE = "course-expired-date"
        const val ASSIGNMENT_DUE_DATE = "assignment-due-date"
        const val CERTIFICATE_AVAILABLE_DATE = "certificate-available-date"
        const val VERIFIED_UPGRADE_DEADLINE = "verified-upgrade-deadline"
        const val VERIFICATION_DEADLINE_DATE = "verification-deadline-date"
    }
}