.PHONY: frontend backend

frontend:
	cd src/main/frontend && npm run dev

backend:
	@if ! systemctl status --no-pager mongod | grep -q "active (running)"; then \
		echo "mongod is not running, trying to start, enter your password"; \
		sudo systemctl start mongod; \
	fi
	mvn install
	java -jar target/action-board-0.0.1-SNAPSHOT.jar
