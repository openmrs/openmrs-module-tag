
angular.module('Tag',['tagService', 'ui.bootstrap'])
    .controller('tagCtrl',function ($scope, TagService) {

    $scope.tags=null;
    $scope.init = function (patientUuid) {
        $scope.patientUuid = patientUuid;
    }
    $scope.tags = TagService.getTag("").then(function (data) {
        tags.add(data);
    }),
        function () {
        };
});
