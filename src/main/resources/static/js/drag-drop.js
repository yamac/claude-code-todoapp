document.addEventListener('DOMContentLoaded', function() {
    console.log('Drag and drop script loaded');
    
    const todoGrid = document.getElementById('todoGrid');
    if (!todoGrid) {
        console.log('Todo grid not found');
        return;
    }
    
    let draggedElement = null;
    
    function initializeDragAndDrop() {
        const todoItems = todoGrid.querySelectorAll('.todo-item');
        console.log('Found ' + todoItems.length + ' todo items');
        
        todoItems.forEach((item, index) => {
            const todoId = item.getAttribute('data-id');
            console.log('Initializing item ' + index + ' with ID: ' + todoId);
            
            // Make sure draggable is set
            item.setAttribute('draggable', 'true');
            
            item.addEventListener('dragstart', handleDragStart);
            item.addEventListener('dragend', handleDragEnd);
            item.addEventListener('dragover', handleDragOver);
            item.addEventListener('drop', handleDrop);
            item.addEventListener('dragenter', handleDragEnter);
            item.addEventListener('dragleave', handleDragLeave);
        });
    }
    
    function handleDragStart(e) {
        console.log('Drag start on item:', this.getAttribute('data-id'));
        draggedElement = this;
        this.classList.add('dragging');
        e.dataTransfer.effectAllowed = 'move';
        e.dataTransfer.setData('text/html', this.innerHTML);
    }
    
    function handleDragEnd(e) {
        console.log('Drag end');
        this.classList.remove('dragging');
        
        // Remove over class from all items
        const items = todoGrid.querySelectorAll('.todo-item');
        items.forEach(item => item.classList.remove('over'));
    }
    
    function handleDragOver(e) {
        if (e.preventDefault) {
            e.preventDefault();
        }
        e.dataTransfer.dropEffect = 'move';
        return false;
    }
    
    function handleDragEnter(e) {
        if (this !== draggedElement) {
            this.classList.add('over');
        }
    }
    
    function handleDragLeave(e) {
        this.classList.remove('over');
    }
    
    function handleDrop(e) {
        console.log('Drop event triggered');
        if (e.stopPropagation) {
            e.stopPropagation();
        }
        
        if (draggedElement !== this) {
            console.log('Reordering items');
            const allItems = Array.from(todoGrid.querySelectorAll('.todo-item'));
            const draggedIndex = allItems.indexOf(draggedElement);
            const targetIndex = allItems.indexOf(this);
            
            if (draggedIndex < targetIndex) {
                this.parentNode.insertBefore(draggedElement, this.nextSibling);
            } else {
                this.parentNode.insertBefore(draggedElement, this);
            }
            
            updateTodoOrder();
        }
        
        return false;
    }
    
    function updateTodoOrder() {
        console.log('Updating todo order');
        const todoItems = todoGrid.querySelectorAll('.todo-item');
        const todoIds = Array.from(todoItems).map(item => {
            return parseInt(item.getAttribute('data-id'));
        });
        
        console.log('New order:', todoIds);
        
        fetch('/api/todos/reorder', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(todoIds)
        })
        .then(response => {
            console.log('Response status:', response.status);
            if (!response.ok) {
                throw new Error('Failed to reorder');
            }
            return response.json();
        })
        .then(data => {
            console.log('Reorder successful:', data);
            window.location.reload();
        })
        .catch(error => {
            console.error('Error reordering todos:', error);
            alert('Failed to save the new order. Please try again.');
            window.location.reload();
        });
    }
    
    // Initialize drag and drop
    initializeDragAndDrop();
});