// document.getElementById('editButton').onclick = function() {
//     if(document.getElementById('editButton')).value = 'Edit Profile')
//     document.getElementById('firstname').removeAttribute('readonly');
//     document.getElementById('surname').removeAttribute('readonly');
//     document.getElementById('mobile').removeAttribute('readonly');
//     document.getElementById('email').removeAttribute('readonly');
//     document.getElementById('address').removeAttribute('readonly');
//     (document.getElementById('editButton')).innerHTML = 'Save Profile';
//     if((document.getElementById('editButton')).value = 'Save Profile'){
//         document.getElementById('firstname').setAttribute('readonly',true);
//     }
// };

function change() {
    var btn = document.getElementById('editButton');
    if (btn.innerHTML == 'Edit Profile') {
        document.getElementById('firstname').removeAttribute('readonly');
        document.getElementById('surname').removeAttribute('readonly');
        document.getElementById('mobile').removeAttribute('readonly');
        document.getElementById('email').removeAttribute('readonly');
        document.getElementById('address').removeAttribute('readonly');
        btn.innerHTML = 'Save Profile';
    } else if(btn.innerHTML == 'Save Profile'){
        document.getElementById('firstname').setAttribute('readonly',true);
        document.getElementById('surname').setAttribute('readonly',true);
        document.getElementById('mobile').setAttribute('readonly',true);
        document.getElementById('email').setAttribute('readonly',true);
        document.getElementById('address').setAttribute('readonly',true);
        btn.innerHTML = 'Edit Profile';
    }
}