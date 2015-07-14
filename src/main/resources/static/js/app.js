angular.module('myApp', [])
    .controller('TodoController', ['$scope', '$http', '$filter', function ($scope, $http, $filter) {
        $scope.greetMe = 'World';

        $scope.addTodo = function () {
            if ($scope.myForm.$invalid) {
                console.log("Formulaire invalide");
                return;
            }

            $http.post('/todos', {task: $scope.todo})
                .success(function (data) {
                    console.log(data);
                    $scope.todos.push(data);
                })
                .error(function () {
                    alert("Impossible de synchroniser la toto-list");
                });

            $scope.myForm.$setPristine();
            $scope.todo = "";
        };

        $scope.dataInit = function () {
            $http.get('/todos').
                success(function (data) {
                    console.log(data);
                    $scope.todos = data;
                }).
                error(function () {
                    alert("Impossible de charger la todo-list");
                });
        };

        $scope.taskDone = function (todo) {
            $http.post('/todos/' + todo._id + '/done')
                .success(function () {
                    todo.done = true;
                })
                .error(function () {
                    alert("Impossible de synchroniser la todo-list");
                });
        };

        $scope.removeTask = function (id) {
            $http.delete('/todos/' + id)
                .success(function () {
                    $scope.todos = $filter('filter')($scope.todos, {_id: '!' + id})
                })
                .error(function () {
                    alert("Impossible de synchroniser la todo-list");
                });
        };
    }]);

angular.element(document).ready(function () {
    angular.bootstrap(document, ['myApp']);
});
