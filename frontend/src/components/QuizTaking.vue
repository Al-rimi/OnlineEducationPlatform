<template>
  <div class="container">
    <h1>{{ quiz?.title }}</h1>
    <div v-if="quiz && questions.length" class="quiz-container">
      <div class="question" v-for="(question, index) in questions" :key="question.id">
        <h3>Question {{ index + 1 }}: {{ question.questionText }}</h3>
        <div class="options">
          <label v-for="option in question.options" :key="option.id" class="option">
            <input type="radio" :name="'question-' + question.id" :value="option.id" v-model="answers[question.id]" />
            {{ option.optionText }}
          </label>
        </div>
      </div>
      <button @click="submitQuiz" class="btn" :disabled="loading">
        {{ loading ? 'Submitting...' : 'Submit Quiz' }}
      </button>
    </div>
    <p v-else>Loading quiz...</p>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { QuizzesApi, QuestionsApi, OptionsApi, QuizResultsApi, UsersApi } from '../services/api';
import { useToast } from '../stores/toast';

const route = useRoute();
const router = useRouter();
const toast = useToast();

const quiz = ref(null);
const questions = ref([]);
const answers = ref({});
const loading = ref(false);
const me = ref(null);

onMounted(async () => {
  try {
    me.value = (await UsersApi.me()).data;
    const { data } = await QuizzesApi.get(route.params.id);
    quiz.value = data;
    // Fetch questions
    const questionsRes = await QuestionsApi.byQuiz(route.params.id);
    const questionsData = questionsRes.data;
    // For each question, fetch options
    const questionsWithOptions = await Promise.all(questionsData.map(async (q) => {
      const optionsRes = await OptionsApi.byQuestion(q.id);
      return { ...q, options: optionsRes.data };
    }));
    questions.value = questionsWithOptions;
  } catch (e) {
    console.error(e);
    toast.error('Failed to load quiz');
  }
});

async function submitQuiz() {
  loading.value = true;
  try {
    const result = {
      quizId: route.params.id,
      userId: me.id, // Assuming me has id
      answers: JSON.stringify(answers.value),
      score: 0 // Calculate score later
    };
    await QuizResultsApi.save(result);
    toast.success('Quiz submitted successfully');
    router.push('/student');
  } catch (e) {
    console.error(e);
    toast.error('Failed to submit quiz');
  } finally {
    loading.value = false;
  }
}
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.quiz-container {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.question {
  margin-bottom: 20px;
}

.options {
  margin-top: 10px;
}

.option {
  display: block;
  margin-bottom: 10px;
  cursor: pointer;
}

.btn {
  padding: 10px 20px;
  background: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 20px;
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
</style>