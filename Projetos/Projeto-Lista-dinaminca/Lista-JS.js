function addItem() {
    var input = document.getElementById("itemInput");
    var item = input.value.trim();
    
    if (item === "") {
        alert("Por favor, digite um item válido.");
        return;
    }
    
    var list = document.getElementById("itemList");
    var listItem = document.createElement("li");
    var itemText = document.createElement("span");
    var buttonGroup = document.createElement("div");
    var deleteBtn = document.createElement("button");
    var editBtn = document.createElement("button");

    itemText.className = "item-text";
    itemText.textContent = item;

    buttonGroup.className = "button-group";

    deleteBtn.innerHTML = '<i class="fas fa-trash"></i> Excluir';
    deleteBtn.className = "delete-btn";
    deleteBtn.onclick = function() {
        listItem.classList.add('fade-out');
        setTimeout(() => listItem.remove(), 300);
    };

    editBtn.innerHTML = '<i class="fas fa-edit"></i> Editar';
    editBtn.className = "edit-btn";
    editBtn.onclick = function() {
        var newText = prompt("Editar item:", itemText.textContent);
        if (newText !== null && newText.trim() !== "") {
            itemText.textContent = newText.trim();
        }
    };

    buttonGroup.appendChild(editBtn);
    buttonGroup.appendChild(deleteBtn);

    listItem.appendChild(itemText);
    listItem.appendChild(buttonGroup);

    list.appendChild(listItem);

    input.value = "";
    input.focus();
}

document.getElementById("itemInput").addEventListener("keypress", function(event) {
    if (event.key === "Enter") {
        event.preventDefault();
        addItem();
    }
});