function closeThisWindowAndParentReload(millisecond) {
    setTimeout(function () {
        parent.layer.closeAll();
        window.parent.location.reload();
    },millisecond);
}

function closeThisWindow(millisecond) {
    setTimeout(function () {
        parent.layer.closeAll();
        window.parent.location.reload();
    },millisecond);
}

function thisWindowReload(millisecond) {
    setTimeout(function () {
        parent.layer.closeAll();
        window.location.reload();
    },millisecond);
}

