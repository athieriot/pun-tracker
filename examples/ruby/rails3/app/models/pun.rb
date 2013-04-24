class Pun < ActiveRecord::Base
  attr_accessible :description, :rating, :image

  mount_uploader :image, ImageUploader

  validates :description,  :presence => true
end
