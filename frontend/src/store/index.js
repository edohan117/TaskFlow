import { createStore } from 'vuex';

function parseJSON(value) {
  try {
    return JSON.parse(value);
  } catch (e) {
    return null;
  }
}

export default createStore({
  state: {
    user: parseJSON(localStorage.getItem('user')) || null, // Parse JSON safely
    isAuthenticated: !!localStorage.getItem('user'), // Check if user data exists
    role: localStorage.getItem('role') || '' // Get role, or default to an empty string
  },
  mutations: {
    SET_USER(state, user) {
      state.user = user;
      state.isAuthenticated = !!user;
      localStorage.setItem('user', JSON.stringify(user)); // Store user data safely
    },
    SET_ROLE(state, role) {
      state.role = role;
      localStorage.setItem('role', role); // Store role in localStorage
    },
    LOGOUT(state) {
      state.user = null;
      state.isAuthenticated = false;
      state.role = '';
      localStorage.removeItem('user'); // Remove user data from localStorage
      localStorage.removeItem('role'); // Remove role data from localStorage
    }
  },
  actions: {
    login({ commit }, { user, role }) {
      commit('SET_USER', user);
      commit('SET_ROLE', role);
    },
    logout({ commit }) {
      commit('LOGOUT');
    }
  },
  getters: {
    isAuthenticated(state) {
      return state.isAuthenticated;
    },
    user(state) {
      return state.user;
    },
    role(state) {
      return state.role; // Return the user's role
    }
  }
});
