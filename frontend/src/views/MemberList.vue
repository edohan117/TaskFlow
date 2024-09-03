<template>
  <div class="member-list">
    <h2>Member List</h2>
    <table>
      <thead>
        <tr>
          <th>Username</th>
          <th>Email</th>
          <th>Role</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="member in members" :key="member.ID">
          <td>{{ member.USERNAME }}</td>
          <td>{{ member.EMAIL }}</td>
          <td>{{ member.ROLE }}</td>
          <td>
            <button @click="editMember(member.ID)">Edit</button>
            <button @click="deleteMember(member.ID)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';

export default {
  name: 'MemberList',
  setup() {
    const members = ref([]);

    const fetchMembers = async () => {
      try {
        const response = await axios.get('/api/member/list');
        members.value = response.data;
      } catch (error) {
        console.error('Error fetching members:', error);
      }
    };

    const editMember = () => {
      // Edit member logic here, possibly using router to navigate to an edit page
    };

    const deleteMember = async (id) => {
      if (confirm('Are you sure you want to delete this member?')) {
        try {
          const response = await axios.delete(`/api/member/delete/${id}`);
          if (response.data.status === 'deleted') {
            alert('Member deleted successfully');
            fetchMembers(); // Refresh the list after deletion
          } else {
            alert('Failed to delete member');
          }
        } catch (error) {
          console.error('Error deleting member:', error);
        }
      }
    };

    onMounted(fetchMembers);

    return {
      members,
      editMember,
      deleteMember,
    };
  },
};
</script>

<style scoped>
.member-list {
  width: 80%;
  margin: 20px auto;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 10px;
  border: 1px solid #ccc;
  text-align: left;
}

th {
  background-color: #f4f4f4;
}

button {
  padding: 5px 10px;
  margin-right: 5px;
  border: none;
  border-radius: 3px;
  cursor: pointer;
}

button:hover {
  background-color: #e2e2e2;
}
</style>
