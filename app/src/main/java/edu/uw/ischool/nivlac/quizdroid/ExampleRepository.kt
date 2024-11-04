package edu.uw.ischool.nivlac.quizdroid

class ExampleRepository : TopicRepository {
    private val topics = listOf(
        Topic(
            title = "Math",
            shortDesc = "This is a short description about math",
            longDesc = "This is a long description about math.",
            questions = mutableListOf(
                Question(
                    questionText = "This is question 1 about math!",
                    answer1 = "This is answer 1! (correct)",
                    answer2 = "This is answer 2!",
                    answer3 = "This is answer 3!",
                    answer4 = "This is answer 4!",
                    correctAnswer = 1),
                Question(
                    questionText = "This is question 2 about math!",
                    answer1 = "This is answer 1!",
                    answer2 = "This is answer 2! (correct)",
                    answer3 = "This is answer 3!",
                    answer4 = "This is answer 4!",
                    correctAnswer = 2),
                Question(
                    questionText = "This is question 3 about math!",
                    answer1 = "This is answer 1!",
                    answer2 = "This is answer 2!",
                    answer3 = "This is answer 3! (correct)",
                    answer4 = "This is answer 4!",
                    correctAnswer = 3)
            )
        ),
        Topic(
            title = "Physics",
            shortDesc = "This is a short description about physics",
            longDesc = "This is a long description about physics.",
            questions = mutableListOf(
                Question(
                    questionText = "This is question 1 about physics!",
                    answer1 = "This is answer 1! (correct)",
                    answer2 = "This is answer 2!",
                    answer3 = "This is answer 3!",
                    answer4 = "This is answer 4!",
                    correctAnswer = 1),
                Question(
                    questionText = "This is question 2 about physics!",
                    answer1 = "This is answer 1!",
                    answer2 = "This is answer 2! (correct)",
                    answer3 = "This is answer 3!",
                    answer4 = "This is answer 4!",
                    correctAnswer = 2),
                Question(
                    questionText = "This is question 3 about physics!",
                    answer1 = "This is answer 1!",
                    answer2 = "This is answer 2!",
                    answer3 = "This is answer 3! (correct)",
                    answer4 = "This is answer 4!",
                    correctAnswer = 3),
                Question(
                    questionText = "This is question 4 about physics!",
                    answer1 = "This is answer 1!",
                    answer2 = "This is answer 2!",
                    answer3 = "This is answer 3!",
                    answer4 = "This is answer 4! (correct)",
                    correctAnswer = 4)
            )
        ),
        Topic(
            title = "Marvel Super Heroes",
            shortDesc = "This is a short description about heroes",
            longDesc = "This is a long description about heroes.",
            questions = mutableListOf(
                Question(
                    questionText = "This is question 1 about heroes!",
                    answer1 = "This is answer 1! (correct)",
                    answer2 = "This is answer 2!",
                    answer3 = "This is answer 3!",
                    answer4 = "This is answer 4!",
                    correctAnswer = 1),
                Question(
                    questionText = "This is question 2 about heroes!",
                    answer1 = "This is answer 1!",
                    answer2 = "This is answer 2! (correct)",
                    answer3 = "This is answer 3!",
                    answer4 = "This is answer 4!",
                    correctAnswer = 2),
                Question(
                    questionText = "This is question 3 about heroes!",
                    answer1 = "This is answer 1!",
                    answer2 = "This is answer 2!",
                    answer3 = "This is answer 3! (correct)",
                    answer4 = "This is answer 4!",
                    correctAnswer = 3),
                Question(
                    questionText = "This is question 4 about heroes!",
                    answer1 = "This is answer 1!",
                    answer2 = "This is answer 2!",
                    answer3 = "This is answer 3!",
                    answer4 = "This is answer 4! (correct)",
                    correctAnswer = 4),
                Question(
                    questionText = "This is question 5 about heroes!",
                    answer1 = "This is answer 1! (correct)",
                    answer2 = "This is answer 2!",
                    answer3 = "This is answer 3!",
                    answer4 = "This is answer 4!",
                    correctAnswer = 1)
            )
        )
    )

    override fun getTopics(): List<Topic> {
        return topics
    }
}