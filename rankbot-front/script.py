import xxhash

def compute_xxhash128(data):
    hash_value = xxhash.xxh128(data).hexdigest()
    return hash_value

# Example data
data = "string"

hash_result = compute_xxhash128(data)
print("xxHash128:", hash_result)
