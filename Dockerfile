FROM mongo:7.0

CMD ["mongod", "--bind_ip_all --ipv6 --auth"]
