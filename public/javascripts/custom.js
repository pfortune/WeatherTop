document.addEventListener('DOMContentLoaded', () => {
        const deleteDialog = document.querySelector('#confirmDeleteDialog');
        const deleteLinks = document.querySelectorAll('#delete');
        const confirmButton = document.querySelector('#confirmDelete');
        const deleteForm = document.querySelector('#deleteForm');

        let deleteUrl;

        deleteLinks.forEach((deleteLink) => {
        deleteLink.addEventListener('click', (e) => {
            e.preventDefault();  // Stop the link from navigating anywhere
            deleteDialog.showModal();  // This function is native to the <dialog> element
            deleteUrl = deleteLink.dataset.link; // Get the URL from the data-link attribute
        });
    });

        deleteForm.addEventListener('submit', (e) => {
        if (!deleteUrl) {
        e.preventDefault();  // Prevent the form from being submitted
    } else {
        deleteForm.action = deleteUrl;  // Set the action of the form to the delete URL
    }
    });

        // Get the cancel button
        const cancelButton = document.querySelector('#cancelDelete');

        // Close the dialog when the cancel button is clicked
        cancelButton.addEventListener('click', () => {
        deleteDialog.close();
    });
});