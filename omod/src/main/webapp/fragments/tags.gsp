<%
    ui.includeJavascript("uicommons", "angular.min.js")
    ui.includeJavascript("tag","app.js")
    ui.includeJavascript("tag", "service/tagService.js")
    ui.includeJavascript("tag", "controller/tagController.js")
    ui.includeJavascript("uicommons", "angular.js")
    ui.includeJavascript("uicommons", "angular-resource.min.js")
    ui.includeJavascript("uicommons", "ngDialog/ngDialog.js")
    ui.includeCss("uicommons", "ngDialog/ngDialog.min.css")

%>

<div class="info-section tags">
    <div class="info-header">
        <i class="icon-tags"></i>
        <h3>${ ui.message("Tags").toUpperCase() }</h3>
        <i class="icon-plus add-action right" onclick=""></i>
    </div>
        <div class="info-body">
        <div id="Tag" ng-controller="tagCtrl">
            <ul>
                <li><a href="#">{{ tags.tag }}</a><i class="icon-remove delete-action right" title="DELETE"></i></li>
            </ul>
    </div>
    </div>
    </div>