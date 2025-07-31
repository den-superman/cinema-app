    document.addEventListener('DOMContentLoaded', function () {
      document.querySelectorAll('.comment-button').forEach(button => {
        button.addEventListener('click', function () {
          const movieId = this.getAttribute('data-movie-id');
          const status = this.getAttribute('status');
          const container = this.closest('li').querySelector('.comments-section');

          if (container.style.display === 'none') {
            fetch(`/movies/${movieId}/comments/${status}`)
              .then(response => {
                if (!response.ok) {
                  throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
              })
              .then(comments => {
                container.innerHTML = `
                  <div class="comment-list">
                    ${comments.map(c => `
                      <div class="comment">
                        <strong>${c.userEmail}</strong>: ${c.text}
                      </div>
                    `).join('')}
                  </div>
                  <form class="comment-form">
                    <input
                      type="text"
                      name="text"
                      placeholder="Write a comment..."
                      required
                      style="width: 100%; padding: 0.5rem; margin-top: 0.5rem; border-radius: 6px; border: 1px solid #ccc;"
                    >
                    <button
                      type="submit"
                      style="margin-top: 0.5rem; padding: 0.5rem 1rem; border: none; background-color: #3498db; color: white; border-radius: 6px; cursor: pointer;"
                    >
                      Submit
                    </button>
                  </form>
                `;

                container.style.display = 'block';
                this.textContent = 'Hide Comments';

                const form = container.querySelector('.comment-form');
                form.addEventListener('submit', function (e) {
                  e.preventDefault();
                  const text = this.text.value.trim();
                  if (!text) return;

                  fetch(`/movies/${movieId}/comments`, {
                    method: 'POST',
                    headers: {
                    'Content-Type': 'text/plain'
                    },
                    body: text
                  })
                    .then(res => {
                      if (!res.ok) throw new Error(`Ошибка: ${res.status}`);
                      return res.json();
                    })
                    .then(newComment => {
                      const list = container.querySelector('.comment-list');
                      const div = document.createElement('div');
                      div.className = 'comment';
                      div.innerHTML = `<strong>${newComment.userEmail}</strong>: ${newComment.text}`;
                      list.appendChild(div);
                      form.reset();
                    })
                    .catch(err => {
                      console.error('Ошибка при добавлении комментария:', err);
                      alert('Не удалось отправить комментарий.');
                    });
                });
              })
              .catch(err => {
                console.error('FETCH ERROR:', err);
                container.innerHTML = '<p>Error loading comments.</p>';
                container.style.display = 'block';
              });
          } else {
            container.style.display = 'none';
            this.textContent = '💬 Comments';
          }
        });
      });
    });
