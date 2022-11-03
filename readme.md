## Running the app

```
./gradlew bootRun
```

## Running tests
```
./gradlew test
```

## Notes

Typically, when dealing with users sessions, we would use more secure methods such as using JWT that could be passed into a request's header or pass through a cookie.  I think having to deal with user authentication is beyond the scope of this exercise, so I am passing in `userId` as arguments that needs a user context.

