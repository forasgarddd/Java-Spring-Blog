//task1
document.querySelector('#h3').addEventListener("click", function () {
    let h3 = document.querySelector('.h3');
    let h6 = document.querySelector('.h6');

    swap(h3, h6)
})

document.querySelector('#h6').addEventListener("click", function () {
    let h3 = document.querySelector('.h3')
    let h6 = document.querySelector('.h6')

    swap(h3, h6)
})

function swap(h3, h6) {
    let temp = h3.innerHTML
    h3.innerHTML = h6.innerHTML
    h6.innerHTML = temp
}

/*//task2
TSIDE1 = 5
TSIDE2 = 8
THEIGHT = 6

document.getElementById('h5')
    .append("Trapeze area = " + ((TSIDE1 + TSIDE2) / 2 * THEIGHT))

//task3

COOKIE = 'divisors'

if(getCookie(COOKIE) !== undefined) {
    showCookie()
} else {
    formCookie()
}

function showCookie() {
    let confirm = window.confirm("The divisors from previous value are: " + getCookie(COOKIE) + ". Save current cookie?")

    if(confirm === true) {
        alert("Cookies were saved, you need to reload the page to delete old and create new cookies")
    } else {
        alert("Cookies were removed, you can set new one")
        removeCookie(COOKIE)
        formCookie()
    }
}

function removeCookie(cookie) {
    setCookie(cookie + "username=Ivan; expires=Thu, 18 Dec 2013 12:00:00 UTC; path=/");
}




function formCookie() {
    let form = document.createElement('form')
    form.id = 'cForm'

    let br = document.createElement('br')

    let label = document.createElement('label')
    label.htmlFor = 'num'
    label.innerHTML = 'Enter your number: '

    let input = document.createElement('input')
    input.type = 'text'
    input.id = 'num'

    let submit = document.createElement('input')
    submit.type = 'submit'
    submit.value = 'submit'
    submit.addEventListener('click', (e) => {
        e.preventDefault()
        solution()
    })

    form.appendChild(label)
    form.appendChild(br)
    form.appendChild(br)
    form.appendChild(input)
    form.appendChild(br)
    form.appendChild(submit)

    document.getElementById('h5').appendChild(form)
}

function solution() {
    let num = document.querySelector('#num').value
    document.getElementById('cForm').remove();
    let br = document.createElement('br');
    document.getElementById('h5').appendChild(br);
    document.getElementById('h5').append("The divisors for number " + num + " are: ");
    for(let i = 1; i <= num; i++) {
        if(num%i===0) {
            document.getElementById('h5').append(i + " ");
            setCookie(i)
        }
    }

}

function setCookie(num) {
    const d = new Date();
    d.setTime(d.getTime() +(24*60*60*1000));
    let expires = "expires" + d.toUTCString();
    document.cookie = COOKIE + "=" + num + ";" + expires + ";path=/";
}

function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for(let i = 0; i <ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}*/
//task4
document.querySelector('.p1').addEventListener('change', () =>  {
    changeToCapitals()
})

document.querySelector('.p1').innerHTML = localStorage.getItem('cap') ?? document.querySelector('.p1').innerHTML


function changeToCapitals() {
    const text = document.getElementById('p1');
    if(document.getElementById('r1').checked === true) {
        text.innerHTML = text.innerHTML.toLowerCase().split(' ').map(s => s.charAt(0).toUpperCase() + s.substring(1)).join(' ');
        let cap = document.querySelector('.p1').innerHTML;
        localStorage.setItem('cap', cap);
    } else if(document.getElementById('r2').checked === true) {
        text.innerHTML = text.innerHTML.toLowerCase().split(' ').map(s => s.charAt(0).toLowerCase() + s.substring(1)).join(' ');
        let cap = document.querySelector('.p1').innerHTML;
        localStorage.setItem('cap', cap);
    }
}

//task5

var imagesObject = [];

function handleFileSelect(evt) {
    var files = evt.target.files; // FileList object

    // Loop through the FileList and render image files as thumbnails.
    for (var i = 0, f; f = files[i]; i++) {

        // Only process image files.
        if (!f.type.match('image.*')) {
            continue;
        }

        var reader = new FileReader();

        // Closure to capture the file information.
        reader.onload = function(e) {
            displayImgData(e.target.result)
            addImage(e.target.result);

        };

        reader.readAsDataURL(f);
    }
}

function loadFromLocalStorage(){
    var images = JSON.parse(localStorage.getItem("images"))

    if(images && images.length > 0){
        imagesObject = images;

        displayNumberOfImgs();
        images.forEach(displayImgData);

    }
}

function addImage(imgData){
    let l = imagesObject.length;
    let b1 = document.createElement("button");
    b1.type = "button";
    b1.id = `b1${l}`;
    b1.innerHTML = "Save pic from ls";
    document.getElementById(`img${l}`).append(b1);
    document.getElementById(`b1${l}`).onclick = function () {
        imagesObject.push(imgData);
        displayNumberOfImgs();
        localStorage.setItem("images", JSON.stringify(imagesObject));
        localStorage.setItem(`img${l}`, imgData);
        document.getElementById(`b1${l}`).remove();
        /*let b2 = document.createElement("button");
        b2.type = "button";
        b2.id = `b2${l}`;
        b2.innerHTML = "Remove pic from ls";
        document.getElementById(`img${l}`).append(b2);
        document.getElementById(`b2${l}`).onclick = function () {
            localStorage.removeItem(`img${l}`)
            var index = imagesObject.indexOf(imgData);
            if (index !== -1) {
                imagesObject.splice(index, 1);
            }
            localStorage.setItem("images", JSON.stringify(imagesObject));
            var images = JSON.parse(localStorage.getItem("images"))
            console.log(images)

            imagesObject = images;

            displayNumberOfImgs();
            //images.forEach(displayImgData);
            document.getElementById(`img${l}`).remove();

        }*/

    }

}



function displayImgData(imgData) {

    let l = imagesObject.length;
    var span = document.createElement('span');
    span.id = `img${imagesObject.length}`;
    span.innerHTML = '<img class="thumb" src="' + imgData + '"/>';
    document.getElementById('list').insertBefore(span, null);
    let b2 = document.createElement("button");
    b2.type = "button";
    b2.id = `b2${l}`;
    b2.innerHTML = "Remove pic from ls";
    document.getElementById(`img${l}`).append(b2);
    document.getElementById(`b2${l}`).onclick = function () {
        localStorage.removeItem(`img${l}`)
        var index = imagesObject.indexOf(imgData);
        if (index !== -1) {
            imagesObject.splice(index, 1);
        }
        localStorage.setItem("images", JSON.stringify(imagesObject));
        var images = JSON.parse(localStorage.getItem("images"))
        console.log(images)

        imagesObject = images;

        displayNumberOfImgs();
        //images.forEach(displayImgData);
        document.getElementById(`img${l}`).remove();

    }
}

function displayNumberOfImgs(){
    if(imagesObject.length > 0){

        document.getElementById("state").innerHTML = imagesObject.length + " image" + ((imagesObject.length > 1) ? "s" : "") + " stored in your browser";

        document.getElementById("deleteImgs").style.display = "inline";

    } else {
        document.getElementById("state").innerHTML = "No images stored in your browser.";
        document.getElementById("deleteImgs").style.display = "none";
    }


}

function deleteImages(){
    imagesObject = [];
    localStorage.removeItem("images");
    displayNumberOfImgs()
    document.getElementById('list').innerHTML = "";
}

document.getElementById('files').addEventListener('change', handleFileSelect, false);
document.getElementById('deleteImgs').addEventListener("click", deleteImages);
loadFromLocalStorage();

function imageForm() {
    var x = document.getElementById('t5');
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

var grid = document.querySelector('.grid');
var msnry;

imagesLoaded( grid, function() {
    // init Isotope after all images have loaded
    msnry = new Masonry( grid, {
        itemSelector: '.grid-item',
        columnWidth: '.grid-sizer',
        percentPosition: true

    });
});






