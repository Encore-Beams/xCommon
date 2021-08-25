package cn.encore.xcommon.api

/**
 *
 * Author: encore
 * Date  : 10/27/20
 * Email : encorebeams@outlook.com
 */
sealed class ResultState<T> {
    /**
     * Describes success state of the UI with
     * [data] shown
     */
    data class SUCCESS<T>(
        val data: T
    ) : ResultState<T>()

    /**
     * Describes loading state of the UI
     */
    class LOADING<T> : ResultState<T>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            return true
        }

        override fun hashCode(): Int = javaClass.hashCode()
    }

    /**
     * Describes Completion state of the UI
     */
    class COMPLETION<T> : ResultState<T>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            return true
        }

        override fun hashCode(): Int = javaClass.hashCode()
    }


    /**
     *  Describes error state of the UI
     */
    data class ERROR<T>(
        val status: Int, val message: String
    ) : ResultState<T>()

    companion object {
        /**
         * Creates [ResultState] object with [SUCCESS] state and [data].
         */
        fun <T> success(data: T): ResultState<T> = SUCCESS(data)

        /**
         * Creates [ResultState] object with [LOADING] state to notify
         * the UI to showing loading.
         */
        fun <T> loading(): ResultState<T> = LOADING()

        /**
         * Creates [ResultState] object with [ERROR] state and [message].
         */
        fun <T> error(status: Int, message: String): ResultState<T> = ERROR(status, message)


        fun <T> completion(): ResultState<T> = COMPLETION()
    }
}