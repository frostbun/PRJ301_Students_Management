let editBtn = document.querySelector("#edit-btn");
let saveBtn = document.querySelector("#save-btn");
let informationForm = document.querySelector("#information-form");
let passwordForm = document.querySelector("#password-form");

let navItems = document.querySelectorAll(".option-edit");
let inputs = informationForm.querySelectorAll("input");

editBtn.onclick = () => {
    editBtn.hidden = true;
    saveBtn.hidden = false;
    inputs.forEach((input) => input.readOnly = false);
};

navItems[0].onclick = () => {
    document.querySelector("#information-form").hidden = false;
    document.querySelector("#password-form").hidden = true;
    navItems[0].classList.add("active");
    navItems[1].classList.remove("active");

    editBtn.hidden = false;
    saveBtn.hidden = true;
    inputs.forEach((input) => input.readOnly = true);
};

navItems[1].onclick = () => {
    document.querySelector("#information-form").hidden = true;
    document.querySelector("#password-form").hidden = false;
    navItems[0].classList.remove("active");
    navItems[1].classList.add("active");
};