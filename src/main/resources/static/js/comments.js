document.addEventListener('DOMContentLoaded', function () {
  document.querySelectorAll('.comment-button').forEach(button => {
    button.dataset.originalText = button.textContent;

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
            const commentsHtml = comments.map(c => {
              let buttonsHtml = '';
              if (status === 'pending') {
                buttonsHtml = `
                  <button class="accept-btn" data-comment-id="${c.id}">Accept</button>
                  <button class="reject-btn" data-comment-id="${c.id}">Reject</button>
                `;
              }
              if (status === 'accepted') {
                if (document.body.dataset.userIsAdmin === "true" || c.userEmail ===  document.body.dataset.userEmail) {
                  buttonsHtml = `
                    <button class="delete-btn" data-comment-id="${c.id}">Delete comment</button>
                  `;
                }

              }
              return `
                <div class="comment">
                  <strong>${c.userEmail}</strong>: ${c.text}
                  ${buttonsHtml}
                </div>
              `;
            }).join('');

            let commentWriteFormHtml = '';
            if (status === 'accepted') {
              commentWriteFormHtml = `
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
                  <div id="comment-status" style="color: green; margin-top: 5px;"></div>
                </form>
              `;
            }

            container.innerHTML = `
              <div class="comment-list">
                ${commentsHtml}
              </div>
              ${commentWriteFormHtml}
            `;

            container.style.display = 'block';
            this.textContent = 'Hide Comments';

            if (status === 'accepted') {
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
                }).catch(err => {
                    console.warn('Fetch failed, but silently ignored (possibly due to logout or page unload):', err);
                });

                form.reset();

                const statusDiv = container.querySelector('#comment-status');
                statusDiv.textContent = 'Thank you for your review. Your comment has been sent to moderation 😇';
              });

              document.querySelectorAll('.delete-btn').forEach(btn => {
                              btn.addEventListener('click', () => {
                                const id = btn.dataset.commentId;
                                fetch(`/movies/${movieId}/comments/${id}`, {
                                  method: 'DELETE',
                                }).then(response => {
                                  if (response.ok) {
                                    btn.closest('.comment').remove();
                                  } else {
                                    console.error('Failed to delete comment');
                                  }
                                });
                              });
                            });

            } else {
              document.querySelectorAll('.accept-btn').forEach(btn => {
                btn.addEventListener('click', () => {
                  const id = btn.dataset.commentId;
                  fetch(`/movies/${movieId}/comments/${id}`, {
                    method: 'PATCH',
                    headers: {
                      'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ status: 'ACCEPTED' })
                  }).then(response => {
                    if (response.ok) {
                      btn.closest('.comment').remove();
                    } else {
                      console.error('Failed to accept comment');
                    }
                  });
                });
              });

              document.querySelectorAll('.reject-btn').forEach(btn => {
                btn.addEventListener('click', () => {
                  const id = btn.dataset.commentId;
                  fetch(`/movies/${movieId}/comments/${id}`, {
                    method: 'PATCH',
                    headers: {
                      'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ status: 'REJECTED' })
                  }).then(response => {
                    if (response.ok) {
                      btn.closest('.comment').remove();
                    } else {
                      console.error('Failed to reject comment');
                    }
                  });
                });
              });
            }
          })
          .catch(err => {
            console.error('FETCH ERROR:', err);
            container.innerHTML = '<p>Error loading comments.</p>';
            container.style.display = 'block';
          });

      } else {
        container.style.display = 'none';
        this.textContent = this.dataset.originalText;
      }
    });
  });
});
